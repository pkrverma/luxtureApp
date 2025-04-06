package `in`.kay.furture.utils

import android.app.Application
import com.razorpay.Checkout
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Checkout.preload(applicationContext)
        instance = this
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
