package `in`.kay.furture

import `in`.kay.furture.models.FurnitureModel
import `in`.kay.furture.screens.AddressData
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    var data = FurnitureModel()

    private val _addressData = mutableStateOf<AddressData?>(null)
    val addressData: State<AddressData?> = _addressData

    fun setAddress(address: AddressData) {
        _addressData.value = address
    }
}
