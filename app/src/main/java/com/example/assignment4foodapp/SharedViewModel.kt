package com.example.assignment4foodapp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val userModel = mutableStateOf<UserModel?>(null)

    fun setUserModel(userModel: UserModel) {
        this.userModel.value = userModel
        Log.d("myTag ", "setUserModel: $userModel, ViewModel HashCode: ${this.hashCode()}")
    }

    fun getUserModel(): UserModel? {
        val user = userModel.value
        Log.d("myTag getUserModel", "getUserModel: $user, ViewModel HashCode: ${this.hashCode()}")
        return user
    }
}
data class CartItem(
    val itemName: String,
    val quantity: Int,
    val price: Double
    // Add other fields as needed
)

data class UserModel(
    val username: String,
    val email: String,
    val userId: String, // Add the userId field if needed
    val cart: List<String>? = emptyList(),
    var phone: String,
    var address: String // Initialize as an empty list
    // Add other fields as needed
)

