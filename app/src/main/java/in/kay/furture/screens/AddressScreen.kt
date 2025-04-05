package `in`.kay.furture.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import `in`.kay.furture.SharedViewModel

data class AddressData(
    val fullName: String,
    val phone: String,
    val address: String,
    val city: String,
    val zip: String,
    val landmark: String = "",
    val state: String = "",
    val alternatePhone: String = "",
    val instructions: String = ""
)

@Composable
fun AddressScreen(navController: NavController, viewModel: SharedViewModel) {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var alternatePhone by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // space for bottom button
                .padding(horizontal = 16.dp, vertical = 8.dp)
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
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = alternatePhone,
                    onValueChange = { alternatePhone = it },
                    label = { Text("Additional Mobile Number (optional)") },
                    singleLine = true,
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

        // Fixed Bottom Button
        Button(
            onClick = {
                if (fullName.isNotBlank() && phone.isNotBlank() && address.isNotBlank() &&
                    city.isNotBlank() && zip.isNotBlank()
                ) {
                    val addressData = AddressData(
                        fullName = fullName,
                        phone = phone,
                        address = address,
                        city = city,
                        zip = zip,
                        landmark = landmark,
                        state = state,
                        alternatePhone = alternatePhone,
                        instructions = instructions
                    )
                    viewModel.setAddress(addressData)
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                }
            },
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
