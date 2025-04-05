import android.app.Activity
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentHandler(private val activity: Activity) : PaymentResultListener {

    fun startPayment(
        modelName: String,
        amount: Int, // In rupees
        userName: String,
        userEmail: String,
        userPhone: String
    ) {
        val checkout = Checkout()
        checkout.setKeyID("YOUR_KEY_ID") // Replace with your Razorpay key

        try {
            val options = JSONObject()
            options.put("name", "Luxurious Creation")
            options.put("description", modelName) // Show model name
            options.put("currency", "INR")
            options.put("amount", amount * 100) // Convert to paise

            // Optional branding image (should be HTTPS)
            options.put("image", "https://luxurious-creation.web.app/logo.png")

            // Optional: Set user info in "prefill"
            val prefill = JSONObject()
            prefill.put("name", userName)
            prefill.put("email", userEmail)
            prefill.put("contact", userPhone)
            options.put("prefill", prefill)

            checkout.open(activity, options)

        } catch (e: Exception) {
            Toast.makeText(activity, "Payment Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        Toast.makeText(activity, "Payment Success: $razorpayPaymentID", Toast.LENGTH_LONG).show()
        // TODO: Update Firestore / show confirmation UI
    }

    override fun onPaymentError(code: Int, response: String?) {
        Toast.makeText(activity, "Payment Failed: $response", Toast.LENGTH_LONG).show()
        // TODO: Show failure screen or retry option
    }
}
