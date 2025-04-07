package `in`.kay.luxture

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import `in`.kay.luxture.screens.*
import `in`.kay.luxture.ui.theme.FurtureTheme
import `in`.kay.luxture.ui.theme.LoginScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    companion object {
        var razorpayHandler: PaymentHandler? = null
    }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                FirebaseAuthHelper.handleGoogleSignInResult(
                    data = data,
                    activity = this@MainActivity,
                    context = this@MainActivity,
                    onSuccess = {
                        Toast.makeText(this@MainActivity, "Google Sign-In Success", Toast.LENGTH_SHORT).show()
                        Log.d("Auth", "Firebase User: ${FirebaseAuth.getInstance().currentUser?.email}")
                    },
                    onFailure = { error ->
                        Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                        Log.e("Auth", "Google Sign-In Error: $error")
                    }
                )
            } else {
                Toast.makeText(this@MainActivity, "Sign-In canceled or failed.", Toast.LENGTH_SHORT).show()
                Log.w("Auth", "Google Sign-In canceled or null data")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)

        setContent {
            FurtureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val viewModel = hiltViewModel<SharedViewModel>()

                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(navController = navController)
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginClick = { navController.navigate("home") },
                                onSignUpClick = { navController.navigate("register") },
                                onForgotPasswordClick = { navController.navigate("forget") },
                                onGoogleSignInSuccess = { navController.navigate("home") }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onRegisterClick = { name, phone, pass, confirm, email ->
                                    FirebaseAuthHelper.signUpWithEmailAndSaveProfile(
                                        context = this@MainActivity,
                                        email = email,
                                        password = pass,
                                        name = name,
                                        phone = phone,
                                        onSuccess = {
                                            Toast.makeText(this@MainActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                                            navController.navigate("login")
                                        },
                                        onFailure = { error ->
                                            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                },
                                onLoginLinkClick = { navController.navigate("login") },
                                onGoogleSignUpClick = { startGoogleSignIn() }
                            )
                        }
                        composable("forget") {
                            ForgotPasswordScreen(
                                onResetClick = { email -> sendPasswordResetEmail(email) },
                                onBackToLoginClick = { navController.navigate("login") }
                            )
                        }
                        composable("home") {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("detail") {
                            DetailScreen(viewModel, navController)
                        }
                        composable("checkout") {
                            CheckoutScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("address") {
                            AddressScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("paymentScreen") {
                            PaymentScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("order") {
                            OrderScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("chairs") {
                            ChairsScreen(
                                onViewDetailClick = { model ->
                                    viewModel.updateSelectedModel(model)
                                    navController.navigate("detail")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun startGoogleSignIn() {
        val signInIntent = FirebaseAuthHelper.getGoogleSignInIntent(this)
        googleSignInLauncher.launch(signInIntent)
    }

    fun sendPasswordResetEmail(email: String) {
        FirebaseAuthHelper.sendPasswordResetEmail(email, this) { success ->
            if (success) {
                Toast.makeText(this, "Reset link sent to $email", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to send reset link", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        Log.d("PaymentSuccess", "Payment ID: $razorpayPaymentId")
        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()
        razorpayHandler?.onSuccess(razorpayPaymentId ?: "NA")
    }

    override fun onPaymentError(errorCode: Int, response: String?, paymentData: PaymentData?) {
        Log.e("PaymentError", "Code: $errorCode | Response: $response")
        Toast.makeText(this, "Payment Failed. Please try again.", Toast.LENGTH_SHORT).show()
        razorpayHandler?.onFailure(response ?: "Unknown error")
    }
}
