package `in`.kay.furture

import `in`.kay.furture.models.FurnitureModel
import `in`.kay.furture.screens.AddressData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    // Observable FurnitureModel data for Compose
    var data by mutableStateOf<FurnitureModel?>(null)
        private set

    fun setSelectedItem(item: FurnitureModel) {
        data = item
    }

    fun clearSelectedItem() {
        data = null
    }

    // Store the selected category name
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun clearCategory() {
        _selectedCategory.value = null
    }

    // Address Data via StateFlow
    private val _address = MutableStateFlow<AddressData?>(null)
    val address: StateFlow<AddressData?> = _address

    fun setAddress(newAddress: AddressData) {
        _address.value = newAddress
    }

    fun clearAddress() {
        _address.value = null
    }
}
