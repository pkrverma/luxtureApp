package `in`.kay.luxture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseAuthHelper {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Email Login
    fun signInWithEmail(
        email: String,
        password: String,
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Login failed")
                }
            }
    }

    // Email Sign-Up with Firestore Document Check
    fun signUpWithEmailAndSaveProfile(
        context: Context,
        email: String,
        password: String,
        name: String,
        phone: String,
        description: String = "",
        profileImage: String = "",
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val emailDocumentId = email.lowercase().trim()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid ?: run {
                        onFailure("User ID is null.")
                        return@addOnCompleteListener
                    }

                    val userProfilesRef = db.collection("userProfiles").document(emailDocumentId)
                    val userDetailRef = db.collection("userDetail").document(emailDocumentId)

                    userProfilesRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (!documentSnapshot.exists()) {
                                val userMap = mapOf(
                                    "uid" to uid,
                                    "email" to email,
                                    "phone" to (phone.ifBlank { "" }),
                                    "name" to (name.ifBlank { "" }),
                                    "description" to (description.ifBlank { "" }),
                                    "profileImage" to (profileImage.ifBlank { "" }),
                                    "createdAt" to FieldValue.serverTimestamp()
                                )

                                userProfilesRef.set(userMap)
                                    .addOnSuccessListener {
                                        val detailMap = mapOf(
                                            "name" to userMap["name"],
                                            "email" to userMap["email"]
                                        )
                                        userDetailRef.set(detailMap)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                                                onSuccess()
                                            }
                                            .addOnFailureListener { e ->
                                                onFailure("Saved profile but failed to save userDetail: ${e.localizedMessage}")
                                            }
                                    }
                                    .addOnFailureListener { e ->
                                        onFailure("Failed to save user profile: ${e.localizedMessage}")
                                    }
                            } else {
                                onSuccess() // Profile already exists
                            }
                        }
                        .addOnFailureListener { e ->
                            onFailure("Error checking user profile: ${e.localizedMessage}")
                        }
                } else {
                    onFailure(task.exception?.localizedMessage ?: "Registration failed.")
                }
            }
    }

    // Forgot Password
    fun sendPasswordResetEmail(
        email: String,
        activity: Activity,
        onComplete: (Boolean) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(activity) { task ->
                onComplete(task.isSuccessful)
            }
    }

    // Logout
    fun signOut() {
        auth.signOut()
    }

    // Google Sign-in Intent
    fun getGoogleSignInIntent(activity: Activity): Intent {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(activity, options)
        return client.signInIntent
    }

    fun handleGoogleSignInResult(
        data: Intent?,
        activity: Activity,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java) ?: run {
                onFailure("Google account is null.")
                return
            }

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity) { firebaseTask ->
                    if (!firebaseTask.isSuccessful) {
                        onFailure(firebaseTask.exception?.message ?: "Google Sign-in failed")
                        return@addOnCompleteListener
                    }

                    val user = FirebaseAuth.getInstance().currentUser
                    if (user == null) {
                        onFailure("Authentication succeeded but user is null.")
                        return@addOnCompleteListener
                    }

                    val db = FirebaseFirestore.getInstance()
                    val email = user.email ?: run {
                        onFailure("Authenticated user has no email.")
                        return@addOnCompleteListener
                    }

                    val uid = user.uid
                    val name = user.displayName ?: account.displayName ?: ""
                    val profileImage = user.photoUrl?.toString() ?: ""
                    val emailDocumentId = email.lowercase().trim()

                    val userProfilesRef = db.collection("userProfiles").document(emailDocumentId)
                    val userDetailRef = db.collection("userDetail").document(emailDocumentId)

                    userProfilesRef.get()
                        .addOnSuccessListener { documentSnapshot ->
                            val isNewUser = !documentSnapshot.exists()

                            val userMap = mapOf(
                                "uid" to uid,
                                "email" to email,
                                "phone" to "",
                                "name" to name,
                                "description" to "",
                                "profileImage" to profileImage,
                                "createdAt" to FieldValue.serverTimestamp()
                            )

                            val detailMap = mapOf(
                                "name" to name,
                                "email" to email
                            )

                            val writeProfileTask = if (isNewUser) {
                                userProfilesRef.set(userMap)
                            } else {
                                userProfilesRef.update(userMap)
                            }

                            writeProfileTask
                                .addOnSuccessListener {
                                    userDetailRef.set(detailMap)
                                        .addOnSuccessListener {
                                            if (isNewUser) {
                                                Toast.makeText(context, "Welcome $name!", Toast.LENGTH_SHORT).show()
                                            }
                                            onSuccess()
                                        }
                                        .addOnFailureListener { e ->
                                            onFailure("Failed to save userDetail: ${e.localizedMessage}")
                                        }
                                }
                                .addOnFailureListener { e ->
                                    onFailure("Failed to save profile: ${e.localizedMessage}")
                                }
                        }
                        .addOnFailureListener { e ->
                            onFailure("Error checking user profile: ${e.localizedMessage}")
                        }
                }
        } catch (e: Exception) {
            onFailure("Exception during Google Sign-in: ${e.localizedMessage}")
        }
    }
}
