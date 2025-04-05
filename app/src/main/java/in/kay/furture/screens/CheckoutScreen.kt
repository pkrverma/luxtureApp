package `in`.kay.furture.screens

import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.navigation.NavHostController
import `in`.kay.furture.ui.theme.Typography
import `in`.kay.furture.ui.theme.colorBlack
import `in`.kay.furture.ui.theme.colorPurple
import `in`.kay.furture.ui.theme.colorWhite
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.kay.furture.models.FurnitureModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CheckoutScreen(navController: NavController, item: FurnitureModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout", color = Color.White) },
                backgroundColor = colorPurple,
                contentColor = colorWhite
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF3F6F8))
                    .padding(16.dp)
            ) {
                // Image and item summary
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorWhite)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = item.drawable),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        item.name?.let { it1 -> Text(it1, style = Typography.h1, fontSize = 20.sp) }
                        item.type?.let { it1 -> Text(it1, style = Typography.body2, color = Color.Gray) }
                        Text("₹${item.price}", style = Typography.h1, fontSize = 20.sp, color = colorPurple)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Order summary
                Text("Order Summary", style = Typography.h1, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorWhite)
                        .padding(16.dp)
                ) {
                    SummaryRow("Subtotal", "₹${item.price}")
                    SummaryRow("Shipping", "₹50.0")
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    SummaryRow("Total", "₹${item.price?.plus(50)}")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // NEW: Navigate to Address Page
                Button(
                    onClick = { navController.navigate("address") },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Add / Edit Delivery Address", fontSize = 16.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm and Pay
                Button(
                    onClick = {
                        // Trigger order or payment flow
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorPurple),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.CenterHorizontally)
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
