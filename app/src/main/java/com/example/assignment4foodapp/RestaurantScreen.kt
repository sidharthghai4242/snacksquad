//@file:Suppress("DEPRECATION")
//
//package com.example.assignment4foodapp
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.LocalImageLoader
//import coil.compose.rememberImagePainter
//import com.google.accompanist.imageloading.LoadPainterDefaults
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.FirebaseApp
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.ktx.auth
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import android.content.SharedPreferences
//import android.util.Log
//import androidx.compose.foundation.lazy.items
//import androidx.compose.ui.draw.drawWithContent
//import com.google.firebase.firestore.ktx.firestore
//import com.google.mlkit.vision.text.Text
//import kotlinx.coroutines.delay
//
//data class Restaurant(
//    val name: String,
//    val address: String,
//    val rating: Double,
//    val delay: Long
//)
//
//class RestaurantScreen : ComponentActivity() {
//    var userId = ""
//    var username = ""
//    var email = " "
//
//    fun getUserDataLocally(sharedPreferences: SharedPreferences?): UserModel {
//        userId = sharedPreferences?.getString("username", "") ?: ""
//        username = sharedPreferences?.getString("email", "") ?: ""
//        email = sharedPreferences?.getString("userId", "") ?: ""
//
//        return UserModel(username, email, userId)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val sharedPreferences = getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
//        val userData = getUserDataLocally(sharedPreferences)
//        val db = Firebase.firestore
//        val restaurants = mutableListOf<Restaurant>()
//
//        // Fetch restaurant data from Firestore and add it to the list
//        db.collection("restraunts").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val name = document["name"] as String
//                    val address = document["address"] as String
//                    val rating = document["rating"] as Double
//                    val delay = (document["delay"] as Number).toLong()
//
//                    val restaurant = Restaurant(name, address, rating, delay)
//                    restaurants.add(restaurant)
//                    Log.d("myTag",restaurants.toString())
//                }
//
//                setContent {
//                    var searchText by remember { mutableStateOf("") }
//
//                    Box(modifier = Modifier.fillMaxSize()) {
//                        Image(
//                            painter = painterResource(id = R.drawable.img_1),
//                            contentDescription = "backgroundImage",
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .drawWithContent {
//                                    drawContent()
//                                    drawRect(Color(0x1D000000)) // Adjust the alpha (0x80) as needed for opacity
//                                },
//                            contentScale = ContentScale.FillBounds // Adjust contentScale as needed
//                        )
//
//                        Surface(
//                            modifier = Modifier
//                                .clip(RoundedCornerShape(0, 0, 8, 8))
//                                .border(2.dp, Color.Black, RoundedCornerShape(0, 0, 8, 8))
//                                .height(130.dp)
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .background(Color(0xFFFF0000)),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Spacer(modifier = Modifier.height(15.dp))
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.Start,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.location),
//                                        contentDescription = null,
//                                        modifier = Modifier
//                                            .size(36.dp)
//                                            .padding(start = 5.dp)
//                                    )
//                                    Text(
//                                        text = "Home",
//                                        style = TextStyle(
//                                            fontSize = 18.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            color = Color.White
//                                        ),
//                                    )
//                                    Box(modifier = Modifier.width(45.dp)) {}
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                                    ) {
//                                        Text(
//                                            text = "Zomato",
//                                            style = TextStyle(
//                                                fontSize = 24.sp,
//                                                fontWeight = FontWeight.Bold,
//                                                textAlign = TextAlign.Center,
//                                                color = Color.Black
//                                            ),
//                                            modifier = Modifier.padding(bottom = 5.dp)
//                                        )
//
//                                        Spacer(modifier = Modifier.width(90.dp))
//
//                                        Image(
//                                            painter = painterResource(id = R.drawable.logout),
//                                            contentDescription = "Logout Icon",
//                                            modifier = Modifier
//                                                .size(24.dp)
//                                                .clickable {
//                                                    CoroutineScope(Dispatchers.IO).launch {
//                                                        try {
//                                                            Firebase.auth.signOut()
//                                                            withContext(Dispatchers.Main) {
//                                                                Toast
//                                                                    .makeText(
//                                                                        this@RestaurantScreen,
//                                                                        "Sign-out successful",
//                                                                        Toast.LENGTH_SHORT
//                                                                    )
//                                                                    .show()
//                                                                val intent = Intent(
//                                                                    this@RestaurantScreen,
//                                                                    MainActivity::class.java
//                                                                )
//                                                                startActivity(intent)
//                                                            }
//                                                        } catch (e: Exception) {
//                                                            e.printStackTrace()
//                                                        }
//                                                    }
//                                                }
//                                        )
//                                    }
//                                }
//
//                                Card(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(56.dp)
//                                        .background(Color(0xFFFF0000))
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .padding(8.dp)
//                                        .border(1.dp, Color(0xFF000000), shape = RoundedCornerShape(10.dp))
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxSize()
//                                            .padding(start = 16.dp),
//                                        contentAlignment = Alignment.CenterStart
//                                    ) {
//                                        BasicTextField(
//                                            value = searchText,
//                                            onValueChange = { searchText = it },
//                                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
//                                            keyboardOptions = KeyboardOptions(
//                                                imeAction = ImeAction.Search
//                                            ),
//                                            keyboardActions = KeyboardActions(
//                                                onSearch = {
//                                                    // Handle search action here
//                                                }
//                                            ),
//                                            singleLine = true,
//                                            modifier = Modifier.fillMaxWidth()
//                                        )
//
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.ic_search),
//                                            contentDescription = null,
//                                            tint = Color.Black,
//                                            modifier = Modifier
//                                                .align(Alignment.CenterEnd)
//                                                .padding(end = 16.dp)
//                                        )
//                                    }
//                                }
//                            }
//                        }
//
//                        Box(modifier = Modifier.fillMaxSize()) {
//                            LazyColumn(
//                                modifier = Modifier
//                                    .padding(14.dp, 140.dp, 14.dp, 14.dp),
//                                verticalArrangement = Arrangement.spacedBy(16.dp)
//                            ) {
//                                items(restaurants) { restaurant ->
//                                    val name1 = restaurant.name
//                                    val address1 = restaurant.address
//                                    val rating1 = restaurant.rating
//                                    Log.d("my Rating at rest screen ", rating1.toString())
//                                    val delay = restaurant.delay
//                                    val images1 = listOf(R.drawable.restraunt1, R.drawable.restraunt2, R.drawable.restraunt3)
//                                    val numImages1 = images1.size
//                                    var currentPage1 by remember { mutableStateOf(0) }
//                                    val randomIndex = (0 until images1.size).random()
//                                    Card(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .height(282.dp)
//                                            .clickable {
//                                                val intent = Intent(this@RestaurantScreen, FoodScreen::class.java)
//                                                intent.putExtra("name", name1)
//                                                intent.putExtra("address", address1)
//                                                intent.putExtra("rating", restaurant.rating)
//                                                intent.putExtra("images", images1.toTypedArray())
//                                                intent.putExtra("currentPage", currentPage1)
//                                                startActivity(intent)
//                                            }
//                                            .padding(8.dp),
//                                        elevation = CardDefaults.cardElevation(
//                                            defaultElevation = 10.dp
//                                        ),
//                                        shape = RoundedCornerShape(16.dp)
//                                    ) {
//                                        Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
//                                            Image(
//                                                painter = rememberImagePainter(
////                                                    data = images1[currentPage1],
//                                                    data = images1[randomIndex],
//                                                    imageLoader = LocalImageLoader.current,
//                                                    builder = {
//                                                        if (true) this.crossfade(LoadPainterDefaults.FadeInTransitionDuration)
//                                                        placeholder(0)
//                                                    }
//                                                ),
//                                                contentDescription = null,
//                                                contentScale = ContentScale.Crop,
//                                                modifier = Modifier
//                                                    .fillMaxWidth()
//                                                    .height(175.dp)
//                                                    .clip(RoundedCornerShape(8.dp))
//                                                    .clickable {
//                                                        val intent = Intent(this@RestaurantScreen, FoodScreen::class.java)
//                                                        intent.putExtra("name", name1)
//                                                        intent.putExtra("address", address1)
//                                                        intent.putExtra("rating", rating1)
//                                                        intent.putExtra("images", images1.toTypedArray())
//                                                        intent.putExtra("currentPage", currentPage1)
//                                                        startActivity(intent)
//                                                    }
//                                            )
//
//                                            Spacer(modifier = Modifier.height(8.dp))
//
//                                            Row(
//                                                modifier = Modifier.fillMaxWidth(),
//                                                horizontalArrangement = Arrangement.SpaceBetween,
//                                                verticalAlignment = Alignment.CenterVertically
//                                            ) {
//                                                Text(
//                                                    text = name1,
//                                                    style = TextStyle(
//                                                        fontSize = 18.sp,
//                                                        fontWeight = FontWeight.Bold,
//                                                        color = Color.Black
//                                                    ),
//                                                    modifier = Modifier.padding(start = 16.dp)
//                                                )
//
//                                                Row(
//                                                    verticalAlignment = Alignment.CenterVertically,
//                                                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                                                ) {
//                                                    Icon(
//                                                        painter = painterResource(id = R.drawable.ic_star),
//                                                        contentDescription = null,
//                                                        tint = Color(0xFFFFC107),
//                                                        modifier = Modifier.size(16.dp)
//                                                    )
//                                                    Text(
//                                                        text = "Rating: $rating1",
//                                                        style = TextStyle(
//                                                            fontSize = 16.sp,
//                                                            color = Color.Gray
//                                                        )
//                                                    )
//                                                }
//                                            }
//
//                                            Text(
//                                                text = "Address: $address1",
//                                                style = TextStyle(
//                                                    fontSize = 14.sp,
//                                                    color = Color.Gray
//                                                ),
//                                                modifier = Modifier.padding(16.dp, 6.dp)
//                                            )
//                                        }
//
//                                        val delayMillis1 = (1) * 1000L
//
//                                        LaunchedEffect(currentPage1) {
//                                            while (true) {
//                                                delay(delayMillis1 + delay)
//                                                currentPage1 = (currentPage1 + 1) % numImages1
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//    }
//}
@file:Suppress("DEPRECATION")

package com.example.assignment4foodapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.google.accompanist.imageloading.LoadPainterDefaults
import com.google.firebase.ktx.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.drawWithContent
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.delay

data class Restaurant(
    val name: String,
    val address: String,
    val rating: Double,
    val delay: Long
)

class RestaurantScreen : ComponentActivity() {
    var userId = ""
    var username = ""
    var email = " "

    fun getUserDataLocally(sharedPreferences: SharedPreferences?): UserModel {
        userId = sharedPreferences?.getString("username", "") ?: ""
        username = sharedPreferences?.getString("email", "") ?: ""
        email = sharedPreferences?.getString("userId", "") ?: ""

        return UserModel(username, email, userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
        val userData = getUserDataLocally(sharedPreferences)
        val db = Firebase.firestore
        val restaurants = mutableListOf<Restaurant>()

        // Fetch restaurant data from Firestore and add it to the list
        db.collection("restraunts").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document["name"] as String
                    val address = document["address"] as String
                    val rating = document["rating"] as Double
                    val delay = (document["delay"] as Number).toLong()

                    val restaurant = Restaurant(name, address, rating, delay)
                    restaurants.add(restaurant)
                    Log.d("myTag",restaurants.toString())
                }

                setContent {
                    var searchText by remember { mutableStateOf("") }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_1),
                            contentDescription = "backgroundImage",
                            modifier = Modifier
                                .fillMaxSize()
                                .drawWithContent {
                                    drawContent()
                                    drawRect(Color(0x1D000000)) // Adjust the alpha (0x80) as needed for opacity
                                },
                            contentScale = ContentScale.FillBounds // Adjust contentScale as needed
                        )

                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(0, 0, 8, 8))
                                .border(2.dp, Color.Black, RoundedCornerShape(0, 0, 8, 8))
                                .height(130.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFF0000)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Spacer(modifier = Modifier.height(15.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.location),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(36.dp)
                                            .padding(start = 5.dp)
                                    )
                                    Text(
                                        text = "Home",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        ),
                                    )
                                    Box(modifier = Modifier.width(45.dp)) {}
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                                    ) {
                                        Text(
                                            text = "Zomato",
                                            style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                textAlign = TextAlign.Center,
                                                color = Color.Black
                                            ),
                                            modifier = Modifier.padding(bottom = 5.dp)
                                        )

                                        Spacer(modifier = Modifier.width(110.dp))
                                        IconButton(onClick = {
                                            val intent = Intent(
                                                this@RestaurantScreen,
                                                ProfileScreen::class.java
                                            )
                                            startActivity(intent)

                                        }) {
                                            Icon(
                                                painter = painterResource(R.drawable.profile_icon), tint = Color.White,
                                                contentDescription = null
                                            )

                                        }


                                    }
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(56.dp)
                                        .background(Color(0xFFFF0000))
                                        .clip(RoundedCornerShape(8.dp))
                                        .padding(8.dp)
                                        .border(1.dp, Color(0xFF000000), shape = RoundedCornerShape(10.dp))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(start = 16.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        BasicTextField(
                                            value = searchText,
                                            onValueChange = { searchText = it },
                                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                                            keyboardOptions = KeyboardOptions(
                                                imeAction = ImeAction.Search
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onSearch = {
                                                    // Handle search action here
                                                }
                                            ),
                                            singleLine = true,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_search),
                                            contentDescription = null,
                                            tint = Color.Black,
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Box(modifier = Modifier.fillMaxSize()) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(14.dp, 140.dp, 14.dp, 14.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                items(restaurants) { restaurant ->
                                    val name1 = restaurant.name
                                    val address1 = restaurant.address
                                    val rating1 = restaurant.rating
                                    Log.d("my Rating at rest screen ", rating1.toString())
                                    val delay = restaurant.delay
                                    val images1 = listOf(R.drawable.restraunt1, R.drawable.restraunt2, R.drawable.restraunt3)
                                    val numImages1 = images1.size
                                    var currentPage1 by remember { mutableStateOf(0) }
                                    val randomIndex = (0 until images1.size).random()
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(282.dp)
                                            .clickable {
                                                val intent = Intent(this@RestaurantScreen, FoodScreen::class.java)
                                                intent.putExtra("name", name1)
                                                intent.putExtra("address", address1)
                                                intent.putExtra("rating", restaurant.rating)
                                                intent.putExtra("images", images1.toTypedArray())
                                                intent.putExtra("currentPage", currentPage1)
                                                startActivity(intent)
                                            }
                                            .padding(8.dp),
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 10.dp
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
                                            Image(
                                                painter = rememberImagePainter(
//                                                    data = images1[currentPage1],
                                                    data = images1[randomIndex],
                                                    imageLoader = LocalImageLoader.current,
                                                    builder = {
                                                        if (true) this.crossfade(LoadPainterDefaults.FadeInTransitionDuration)
                                                        placeholder(0)
                                                    }
                                                ),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(175.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .clickable {
                                                        val intent = Intent(this@RestaurantScreen, FoodScreen::class.java)
                                                        intent.putExtra("name", name1)
                                                        intent.putExtra("address", address1)
                                                        intent.putExtra("rating", rating1)
                                                        intent.putExtra("images", images1.toTypedArray())
                                                        intent.putExtra("currentPage", currentPage1)
                                                        startActivity(intent)
                                                    }
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = name1,
                                                    style = TextStyle(
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Black
                                                    ),
                                                    modifier = Modifier.padding(start = 16.dp)
                                                )

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                                                ) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_star),
                                                        contentDescription = null,
                                                        tint = Color(0xFFFFC107),
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Text(
                                                        text = "Rating: $rating1",
                                                        style = TextStyle(
                                                            fontSize = 16.sp,
                                                            color = Color.Gray
                                                        )
                                                    )
                                                }
                                            }

                                            Text(
                                                text = "Address: $address1",
                                                style = TextStyle(
                                                    fontSize = 14.sp,
                                                    color = Color.Gray
                                                ),
                                                modifier = Modifier.padding(16.dp, 6.dp)
                                            )
                                        }

                                        val delayMillis1 = (1) * 1000L

                                        LaunchedEffect(currentPage1) {
                                            while (true) {
                                                delay(delayMillis1 + delay)
                                                currentPage1 = (currentPage1 + 1) % numImages1
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }
}