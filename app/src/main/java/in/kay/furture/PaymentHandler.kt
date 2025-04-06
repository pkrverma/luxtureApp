package `in`.kay.furture

import android.app.Activity
import com.razorpay.Checkout
import org.json.JSONObject

class PaymentHandler(
    private val activity: Activity,
    private val onSuccess: (paymentId: String) -> Unit,
    private val onFailure: (errorMessage: String) -> Unit
) {
    fun startPayment(
        itemName: String,
        amountInRupees: Int,
        userName: String,
        userEmail: String,
        userPhone: String
    ) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_5QQF6Zeq0JBypN")
        Checkout.preload(activity.applicationContext)

        try {
            val options = JSONObject().apply {
                put("name", "Luxurious Creation")
                put("description", itemName)
                put("currency", "INR")
                put("amount", amountInRupees * 100)

                put("image", "https://your-image-link.com/logo.png")

                val prefill = JSONObject().apply {
                    put("name", userName)
                    put("email", userEmail)
                    put("contact", userPhone)
                }

                put("prefill", prefill)
            }

            // Register this handler with MainActivity before starting payment
            MainActivity.razorpayHandler = this
            checkout.open(activity, options)
        } catch (e: Exception) {
            onFailure("Payment failed: ${e.localizedMessage}")
        }
    }

    // Called from MainActivity on success
    fun onSuccess(paymentId: String) {
        onSuccess.invoke(paymentId)
        clear()
    }

    // Called from MainActivity on failure
    fun onFailure(errorMessage: String) {
        onFailure.invoke(errorMessage)
        clear()
    }

    // Clean up to prevent memory leaks
    private fun clear() {
        MainActivity.razorpayHandler = null
    }
}
