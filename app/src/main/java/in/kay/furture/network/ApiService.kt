package `in`.kay.furture.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class OrderRequest(val amount: Int)

data class RazorpayOrderResponse(
    val success: Boolean,
    val order: RazorpayOrder
)

data class RazorpayOrder(
    val id: String,
    val amount: Int,
    val currency: String
)

interface ApiService {
    @POST("createOrder")
    suspend fun createOrder(@Body request: OrderRequest): Response<RazorpayOrderResponse>
}
