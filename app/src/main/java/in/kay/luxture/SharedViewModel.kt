package `in`.kay.luxture

import `in`.kay.luxture.models.FurnitureModel
import `in`.kay.luxture.screens.AddressData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    // Observable selected item
    private val _selectedItem = MutableStateFlow<FurnitureModel?>(null)
    val selectedItem: StateFlow<FurnitureModel?> = _selectedItem

    fun setSelectedItem(item: FurnitureModel) {
        _selectedItem.value = item
    }

    fun clearSelectedItem() {
        _selectedItem.value = null
    }

    // Selected category
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun clearCategory() {
        _selectedCategory.value = null
    }

    // Address
    private val _address = MutableStateFlow<AddressData?>(null)
    val address: StateFlow<AddressData?> = _address

    fun setAddress(newAddress: AddressData) {
        _address.value = newAddress
    }

    fun clearAddress() {
        _address.value = null
    }

    private val _paymentId = MutableStateFlow<String?>(null)
    val paymentId: StateFlow<String?> = _paymentId

    fun setPaymentId(id: String) {
        _paymentId.value = id
    }

    fun clearPaymentId() {
        _paymentId.value = null
    }

    private val _paymentMethod = MutableStateFlow<String?>(null)
    val paymentMethod: StateFlow<String?> = _paymentMethod

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }

    private val _addresses = MutableStateFlow<List<AddressData>>(emptyList())
    val addresses: StateFlow<List<AddressData>> = _addresses

    fun addAddress(newAddress: AddressData) {
        val current = _addresses.value.toMutableList()

        current.add(0, newAddress)
        if (current.size > 2) current.removeLast()

        _addresses.value = current
    }

}
