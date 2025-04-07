package `in`.kay.luxture.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FurnitureModel(
    val name: String = "",
    val drawable: Int = 0,
    val link: String = "",
    val price: Int = 0,
    val description: String = "",
    val type: String = ""
) : Parcelable


