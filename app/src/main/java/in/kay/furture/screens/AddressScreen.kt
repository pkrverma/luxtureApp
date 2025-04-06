package `in`.kay.furture.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import `in`.kay.furture.SharedViewModel
import `in`.kay.furture.ui.theme.colorPurple
import `in`.kay.furture.ui.theme.colorWhite

data class AddressData(
    val fullName: String,
    val phone: String,
    val email: String,
    val address: String,
    val city: String,
    val zip: String,
    val landmark: String = "",
    val state: String = "",
    val alternatePhone: String = "",
    val instructions: String = ""
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddressScreen(navController: NavController, viewModel: SharedViewModel) {
    val context = LocalContext.current
    val existingAddress by viewModel.address.collectAsState()

    var fullName by remember { mutableStateOf(existingAddress?.fullName ?: "") }
    var phone by remember { mutableStateOf(existingAddress?.phone ?: "") }
    var email by remember { mutableStateOf(existingAddress?.email ?: "") }
    var address by remember { mutableStateOf(existingAddress?.address ?: "") }
    var city by remember { mutableStateOf(existingAddress?.city ?: "") }
    var zip by remember { mutableStateOf(existingAddress?.zip ?: "") }
    var landmark by remember { mutableStateOf(existingAddress?.landmark ?: "") }
    var state by remember { mutableStateOf(existingAddress?.state ?: "") }
    var alternatePhone by remember { mutableStateOf(existingAddress?.alternatePhone ?: "") }
    var instructions by remember { mutableStateOf(existingAddress?.instructions ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Delivery Address", color = colorWhite) },
                backgroundColor = colorPurple
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(bottom = 80.dp)
            ) {
                item {
                    Text("Enter Delivery Details", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Primary Mobile Number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = alternatePhone,
                        onValueChange = { alternatePhone = it },
                        label = { Text("Additional Mobile Number (optional)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Full Address") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = landmark,
                        onValueChange = { landmark = it },
                        label = { Text("Landmark (optional)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("City") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = state,
                            onValueChange = { state = it },
                            label = { Text("State") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = zip,
                        onValueChange = { zip = it },
                        label = { Text("ZIP Code") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = instructions,
                        onValueChange = { instructions = it },
                        label = { Text("Delivery Instructions (optional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (fullName.isNotBlank() && phone.isNotBlank() && email.isNotBlank() &&
                        address.isNotBlank() && city.isNotBlank() && zip.isNotBlank()
                    ) {
                        val newAddress = AddressData(
                            fullName = fullName,
                            phone = phone,
                            email = email,
                            address = address,
                            city = city,
                            zip = zip,
                            landmark = landmark,
                            state = state,
                            alternatePhone = alternatePhone,
                            instructions = instructions
                        )
                        viewModel.setAddress(newAddress)
                        Toast.makeText(context, "Address saved!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorPurple),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text("Save and Continue", color = Color.White)
            }
        }
    }
}
