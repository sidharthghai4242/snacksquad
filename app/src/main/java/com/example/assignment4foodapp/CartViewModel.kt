/*
package com.example.assignment4foodapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel



class CartViewModel : ViewModel() {
    val cartItems = mutableStateListOf<CartItem>()

    fun addItemToCart(dish: Dish) {
        val existingItem = cartItems.find { it.itemName == dish }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(dish,1,0.0))
        }
    }

    fun removeItemFromCart(item: CartItem) {
        cartItems.remove(item)
    }
}
*/
