package `in`.kay.furture

import `in`.kay.furture.screens.*
import `in`.kay.furture.ui.theme.FurtureTheme
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    // Global variable to reference payment callback handler
    companion object {
        var razorpayHandler: PaymentHandler? = null
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
                            SplashScreen(navController)
                        }
                        composable("home") {
                            HomeScreen(navController, viewModel)
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
                    }
                }
            }
        }
    }

    // Razorpay success callback
    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        Log.d("PaymentSuccess", "Payment ID: $razorpayPaymentId")
        Log.d("PaymentDetails", "Order ID: ${paymentData?.orderId}, Signature: ${paymentData?.signature}")

        Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show()

        // Forward to PaymentHandler
        razorpayHandler?.onSuccess(razorpayPaymentId ?: "NA")
    }

    // Razorpay failure callback
    override fun onPaymentError(errorCode: Int, response: String?, paymentData: PaymentData?) {
        Log.e("PaymentError", "Code: $errorCode | Response: $response")
        Log.d("PaymentDetails", "Order ID: ${paymentData?.orderId}")

        Toast.makeText(this, "Payment Failed. Please try again.", Toast.LENGTH_SHORT).show()

        // Forward to PaymentHandler
        razorpayHandler?.onFailure(response ?: "Unknown error")
    }
}
