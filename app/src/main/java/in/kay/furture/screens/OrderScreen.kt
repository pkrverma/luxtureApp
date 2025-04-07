package `in`.kay.furture.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import `in`.kay.furture.SharedViewModel
import `in`.kay.furture.ui.theme.Typography
import `in`.kay.furture.ui.theme.colorPurple
import `in`.kay.furture.ui.theme.colorWhite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    navController: NavController,
    viewModel: SharedViewModel
) {
    val item by viewModel.selectedItem.collectAsStateWithLifecycle()
    val address by viewModel.address.collectAsStateWithLifecycle()
    val paymentId by viewModel.paymentId.collectAsStateWithLifecycle()
    val paymentStatus = "Success"
    val paymentMethod by viewModel.paymentMethod.collectAsState()

    val now = remember { LocalDateTime.now() }
    val orderFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
    val deliveryFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val orderTime = now.format(orderFormatter)

    val expectedDelivery = remember(item) {
        val daysToAdd = when {
            item?.price ?: 0 < 3000 -> 3L
            item?.price ?: 0 < 10000 -> 5L
            else -> 7L
        }
        now.plus(daysToAdd, ChronoUnit.DAYS).format(deliveryFormatter)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Summary", color = colorWhite) },
                backgroundColor = colorPurple
            )
        },
        content = {
            if (item == null || address == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No order details found.", style = Typography.h1)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF3F6F8))
                        .padding(16.dp)
                ) {
                    Text(
                        "Your order has been placed!",
                        style = Typography.h1,
                        fontSize = 20.sp,
                        color = Color(red = 0, green = 128, blue = 0)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = 6.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Product Details", style = Typography.body1, color = colorPurple)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = item!!.drawable),
                                    contentDescription = item!!.name,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .padding(end = 12.dp)
                                )
                                Column {
                                    Text("Name: ${item!!.name}", style = Typography.body1)
                                    Text("Category: ${item!!.type}", style = Typography.body1)
                                    Text("Price: ₹${item!!.price}", style = Typography.body1)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Delivery Address", style = Typography.body1, color = colorPurple)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Name: ${address!!.fullName}")
                            Text("Phone: ${address!!.phone}")
                            Text("Address: ${address!!.address}, ${address!!.city}, ${address!!.state} - ${address!!.zip}")
                            if (address!!.landmark.isNotBlank()) {
                                Text("Landmark: ${address!!.landmark}")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Order Detail", style = Typography.body1, color = colorPurple)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Order Date: $orderTime")
                            Text("Delivery Charge: ₹50")
                            Text("Total Amount Paid: ₹${(item!!.price ?: 0) + 50}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Payment Method: ${paymentMethod ?: "Not Selected"}")
                            Text("Payment ID: ${paymentId ?: "Not Available"}")
                            Text("Status: $paymentStatus")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Expected Delivery: $expectedDelivery")
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorPurple)
                    ) {
                        Text("Back to Home", color = Color.White)
                    }
                }
            }
        }
    )
}
