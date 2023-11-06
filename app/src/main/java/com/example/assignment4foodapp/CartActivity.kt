import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

/*import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import com.example.assignment4foodapp.CartItemRow
import com.example.assignment4foodapp.Dish

@SuppressLint("RestrictedApi")
class CartActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val cartItems = mutableListOf<Dish>() // Initialize an empty cart
            CartContent(cartItems)
        }
    }

    @Composable
    fun CartContent(cartItems: MutableList<Dish>) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Display cart items here
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(cartItems) { dish ->
                        CartItemRow(dish = dish)
                    }
                }
            }
        }
    }
}*//*



import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
//import com.example.assignment4foodapp.CartItemRow
import com.example.assignment4foodapp.CartItems
import com.google.firebase.firestore.FirebaseFirestore

data class CartItem(
    val dishName: String,
    val dishCost: String,
    val restaurantName: String
)

class CartActivity : ComponentActivity() {
    private val cartItems = mutableStateListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch user's cart data
        val email = intent.getStringExtra("email") ?: ""
        fetchUserCartData(email)

        setContent {
            CartScreen(cartItems = cartItems)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun CartScreen(cartItems: List<CartItem>) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Shopping Cart") }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (cartItems.isEmpty()) {
                    Text(text = "Your cart is empty.", fontSize = 20.sp)
                } else {
                    CartList(cartItems = cartItems)
                }
            }
        }
    }

    private @Composable
    fun CartList(cartItems: List<CartItem>) {

    }


    private fun fetchUserCartData(email: String) {
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        // Build a reference to the user's document based on their email
        val userDocumentRef = usersCollection.whereEqualTo("email", email).limit(1)

        userDocumentRef.get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDocument = documents.documents[0]

                    // Get the user's cart data
                    val cartData = userDocument.get("cart") as? List<Map<String, Any>>

                    if (cartData != null) {
                        // Convert the cart data to CartItem objects
                        val cartItems = cartData.map { cartItemData ->
                            CartItem(
                                dishName = cartItemData["dishName"] as String,
                                dishCost = cartItemData["dishCost"] as String,
                                restaurantName = cartItemData["restaurantName"] as String
                            )
                        }

                        // Update the local cartItems list with the fetched data
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("CartActivity", "Error fetching user cart data", e)
            }
    }
}


*/
// Define a CartItem data class to represent cart items
data class CartItem(
    val dishName: String,
    val dishCost: String,
    val restaurantName: String
)

// Fetch user-specific cart data from Firestore
fun fetchCartData(email: String, onSuccess: (List<CartItem>) -> Unit, onFailure: (Exception) -> Unit) {
    val db = Firebase.firestore
    val cartItems = mutableListOf<CartItem>()

    db.collection("users")
        .whereEqualTo("email", email)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val cart = document.data["cart"] as List<Map<String, Any>>?
                if (cart != null) {
                    for (item in cart) {
                        val dishName = item["dishName"].toString()
                        val dishCost = item["dishCost"].toString()
                        val restaurantName = item["restaurantName"].toString()
                        cartItems.add(CartItem(dishName, dishCost, restaurantName))
                    }
                }
            }
            onSuccess(cartItems)
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}

// CartActivity Composable to display the cart data
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartActivity(email: String) {
    var cartItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }

    fetchCartData(
        email = email,
        onSuccess = { cartData ->
            cartItems = cartData
        },
        onFailure = { e -> "Error fetching cart data: ${e.message}"
            // Handle error
            // You can show an error message or take appropriate action here
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Shopping Cart") }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                Text(text = "Your cart is empty.", fontSize = 20.sp)
            } else {
                CartList(cartItems = cartItems)
            }
        }
    }
}

// CartList Composable to display the list of cart items
@Composable
fun CartList(cartItems: List<CartItem>) {
    LazyColumn {
        items(cartItems) { cartItem ->
            // Create a Composable to display each cart item
            Text(text = cartItem.dishName)
            Text(text = cartItem.dishCost)
            Text(text = cartItem.restaurantName)
        }
    }
}

@SuppressLint("RestrictedApi")
class CActivity : ComponentActivity() {
    var userId = ""
    var username = ""
    var email = " "

    fun getUserDataLocally(sharedPreferences: SharedPreferences?): UserModel {
        userId = sharedPreferences?.getString("username", "") ?: ""
        username = sharedPreferences?.getString("email", "") ?: ""
        email = sharedPreferences?.getString("userId", "") ?: ""

        return UserModel(username, email, userId)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val userData = getUserDataLocally(sharedPreferences)
        setContent {
            CartActivity(userData.email)
        }
    }
}

