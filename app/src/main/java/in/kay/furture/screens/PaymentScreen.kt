package `in`.kay.furture.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.kay.furture.PaymentHandler
import `in`.kay.furture.SharedViewModel
import `in`.kay.furture.ui.theme.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PaymentScreen(navController: NavController, viewModel: SharedViewModel) {
    val context = LocalContext.current
    val activity = context as Activity
    val selectedItem by viewModel.selectedItem.collectAsState()
    val address by viewModel.address.collectAsState()

    if (selectedItem == null || address == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Missing order details or address.", style = Typography.h1)
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment", color = colorWhite) },
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
                Text("Select Payment Method", style = Typography.h1, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))

                var selectedMethod by remember { mutableStateOf("UPI") }

                // Set initial value to SharedViewModel
                LaunchedEffect(selectedMethod) {
                    viewModel.setPaymentMethod(selectedMethod)
                }

                PaymentOption(
                    title = "NET Banking",
                    selected = selectedMethod == "NET Banking",
                    onSelected = {
                        selectedMethod = "NET Banking"
                        viewModel.setPaymentMethod("NET Banking")
                    }
                )
                PaymentOption(
                    title = "Credit / Debit Card",
                    selected = selectedMethod == "Card",
                    onSelected = {
                        selectedMethod = "Card"
                        viewModel.setPaymentMethod("Card")
                    }
                )
                PaymentOption(
                    title = "Cash on Delivery",
                    selected = selectedMethod == "COD",
                    onSelected = {
                        selectedMethod = "COD"
                        viewModel.setPaymentMethod("COD")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        when (selectedMethod) {
                            "Card", "NET Banking" -> {
                                val paymentHandler = PaymentHandler(
                                    activity = activity,
                                    onSuccess = { paymentId ->
                                        viewModel.setPaymentId(paymentId)
                                        navController.navigate("order") {
                                            popUpTo("checkout") { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    },
                                    onFailure = { errorMessage ->
                                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                                    }
                                )
                                paymentHandler.startPayment(
                                    itemName = selectedItem!!.name ?: "Furniture Item",
                                    amountInRupees = selectedItem!!.price ?: 0,
                                    userName = address!!.fullName,
                                    userEmail = address!!.email,
                                    userPhone = address!!.phone
                                )
                            }

                            "COD" -> {
                                viewModel.setPaymentId("COD_REF_${System.currentTimeMillis()}")
                                navController.navigate("order") {
                                    popUpTo("checkout") { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedMethod.isNullOrBlank()) Color.Gray else colorPurple),
                    enabled = !selectedMethod.isNullOrBlank()
                ) {
                    Text("Pay Now", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    )
}

@Composable
fun PaymentOption(title: String, selected: Boolean, onSelected: () -> Unit) {
    Card(
        backgroundColor = if (selected) colorPurple else colorWhite,
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(if (selected) colorPurple else colorWhite)
                .clickable { onSelected() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selected,
                onClick = onSelected,
                colors = RadioButtonDefaults.colors(
                    selectedColor = colorWhite,
                    unselectedColor = colorPurple
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = if (selected) colorWhite else colorBlack,
                fontSize = 16.sp
            )
        }
    }
}
