package `in`.kay.luxture.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.kay.luxture.SharedViewModel
import `in`.kay.luxture.ui.theme.Typography
import `in`.kay.luxture.ui.theme.colorPurple
import `in`.kay.luxture.ui.theme.colorWhite

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CheckoutScreen(
    navController: NavController,
    viewModel: SharedViewModel
) {
    val context = LocalContext.current
    val activity = context as Activity
    val addressState by viewModel.address.collectAsState()
    val item by viewModel.selectedItem.collectAsState()


    if (item == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("No item selected for checkout.", style = Typography.h1)
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout", color = colorWhite) },
                backgroundColor = colorPurple
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF3F6F8))
                    .padding(16.dp)
            ) {
                // Image and item details
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorWhite)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = item!!.drawable),
                        contentDescription = item!!.name,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(item!!.name ?: "Unknown Product", style = Typography.h1, fontSize = 20.sp)
                        Text(item!!.type ?: "Unknown Type", style = Typography.body2, color = Color.Gray)
                        Text(
                            "₹${item!!.price ?: 0}",
                            style = Typography.h1,
                            fontSize = 20.sp,
                            color = colorPurple
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Order Summary
                Text("Order Summary", style = Typography.h1, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))

                val subtotal = item!!.price ?: 0
                val shipping = 50
                val total = subtotal + shipping

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorWhite)
                        .padding(16.dp)
                ) {
                    SummaryRow("Subtotal", "₹$subtotal")
                    SummaryRow("Shipping", "₹$shipping")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    SummaryRow("Total", "₹$total")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Address section
                addressState?.let { address ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(colorWhite)
                            .padding(16.dp)
                    ) {
                        Text("Deliver To:", style = Typography.h1, fontSize = 16.sp, color = colorPurple)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${address.fullName}, ${address.phone}")
                        Text("${address.address}, ${address.city}, ${address.state} - ${address.zip}")
                        if (address.landmark.isNotBlank()) {
                            Text("Landmark: ${address.landmark}")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Add / Edit Address Button
                Button(
                    onClick = { navController.navigate("address") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Add / Edit Delivery Address", fontSize = 16.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm and Pay Button
                Button(
                    onClick = {
                        if (addressState == null) {
                            Toast.makeText(context, "Please add your delivery address first.", Toast.LENGTH_SHORT).show()
                            navController.navigate("address")
                        } else {
                            navController.navigate("paymentScreen")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorPurple),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Confirm and Pay", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    )
}

@Composable
fun SummaryRow(label: String, amount: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = Typography.body1)
        Text(amount, style = Typography.body1)
    }
}
