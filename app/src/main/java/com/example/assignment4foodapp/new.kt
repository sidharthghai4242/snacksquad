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
//import androidx.compose.ui.draw.drawWithContent
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.delay
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
//
//class RestaurantScreen : ComponentActivity() {
//    var userId=""
//    var username=""
//    var email = " "
//    fun getUserDataLocally(sharedPreferences: SharedPreferences?): UserModel {
//        userId = sharedPreferences?.getString("username", "") ?: ""
//        username = sharedPreferences?.getString("email", "") ?: ""
//        email = sharedPreferences?.getString("userId", "") ?: ""
//
//        return UserModel(username, email,userId)
//    }
//    @OptIn(ExperimentalMaterial3Api::class)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        val sharedPreferences = getSharedPreferences("your_preference_name", Context.MODE_PRIVATE)
//        val userData = getUserDataLocally(sharedPreferences)
//        super.onCreate(savedInstanceState)
//        setContent {
//            var searchText by remember { mutableStateOf("") }
//
//            Box(modifier = Modifier.fillMaxSize()) {
//                Image(
//                    painter = painterResource(id = R.drawable.img_1),
//                    contentDescription = "backgroundImage",
//                    modifier = Modifier.fillMaxSize()
//                        .drawWithContent {
//                            drawContent()
//                            drawRect(Color(0x1D000000)) // Adjust the alpha (0x80) as needed for opacity
//                        },
//                    contentScale = ContentScale.FillBounds // Adjust contentScale as needed
//                )
//
//                Surface(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(0, 0, 8, 8))
//                        .border(2.dp, Color.Black, RoundedCornerShape(0, 0, 8, 8))
//                        .height(130.dp)
//                ) {
//
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color(0xFFFF0000)),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        Spacer(modifier = Modifier.height(15.dp))
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Start,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.location),
//                                contentDescription = null,
//                                modifier = Modifier.size(36.dp).padding(start=5.dp)
//                            )
//                            Text(
//                                text = "Home",
//                                style = TextStyle(
//                                    fontSize = 18.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color.White
//                                ),
//                            )
//                            Box(modifier = Modifier.width(45.dp)){}
//                            Row(
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween, // Center the "Zomato" text and add space for the logout icon
//                                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                            ) {
//                                Text(
//                                    text = "Zomato",
//                                    style = TextStyle(
//                                        fontSize = 24.sp,
//                                        fontWeight = FontWeight.Bold,
//                                        textAlign = TextAlign.Center,
//                                        color = Color.Black
//                                    ),
//                                    modifier = Modifier.padding(bottom = 5.dp)
//                                )
//
//                                // Spacer to add space between "Zomato" text and the logout icon
//                                Spacer(modifier = Modifier.width(90.dp))
//
//                                // Add the logout icon here
//                                Image(
//                                    painter = painterResource(id = R.drawable.logout), // Replace with the actual drawable resource for the logout icon
//                                    contentDescription = "Logout Icon",
//                                    modifier = Modifier
//                                        .size(24.dp) // Adjust the size of the logout icon as needed
//                                        .clickable { // Handle click events for logout
//                                            // Use a CoroutineScope to handle asynchronous sign-out
//                                            CoroutineScope(Dispatchers.IO).launch {
//                                                try {
//                                                    Firebase.auth.signOut()
//                                                    withContext(Dispatchers.Main) {
//                                                        // Show a toast when sign-out is successful
//                                                        Toast.makeText(
//                                                            this@RestaurantScreen,
//                                                            "Sign-out successful",
//                                                            Toast.LENGTH_SHORT
//                                                        ).show()
//                                                        // Redirect to MainActivity after sign-out is completed
//                                                        val intent = Intent(this@RestaurantScreen, MainActivity::class.java)
//                                                        startActivity(intent)
//                                                    }
//                                                } catch (e: Exception) {
//                                                    // Handle sign-out error, if any
//                                                    e.printStackTrace()
//                                                }
//                                            }
//                                        }
//                                )
//                            }
//                        }
//
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(56.dp)
//                                .background(Color(0xFFFF0000))
//                                .clip(RoundedCornerShape(8.dp))
//                                .padding(8.dp)
//                                .border(1.dp, Color(0xFF000000), shape = RoundedCornerShape(10.dp))
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .padding(start = 16.dp),
//                                contentAlignment = Alignment.CenterStart
//                            ) {
//                                BasicTextField(
//                                    value = searchText,
//                                    onValueChange = { searchText = it },
//                                    textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
//                                    keyboardOptions = KeyboardOptions(
//                                        imeAction = ImeAction.Search
//                                    ),
//                                    keyboardActions = KeyboardActions(
//                                        onSearch = {
//                                            // Handle search action here
//                                        }
//                                    ),
//                                    singleLine = true,
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//
//                                Icon(
//                                    painter = painterResource(id = R.drawable.ic_search),
//                                    contentDescription = null,
//                                    tint = Color.Black,
//                                    modifier = Modifier
//                                        .align(Alignment.CenterEnd)
//                                        .padding(end = 16.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//                LazyColumn(
//                    modifier = Modifier
//                        .padding(14.dp,140.dp,14.dp,14.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    item {
//                        // Restaurant Card 1
//                        val name1 = "Indian Accent"
//                        val address1 = "New Delhi"
//                        val rating1 = 4.5f
//                        val images1 = listOf(R.drawable.restraunt1, R.drawable.restraunt2, R.drawable.restraunt3)
//                        val numImages1 = images1.size
//                        var currentPage1 by remember { mutableStateOf(0) }
//
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(282.dp)
//                                .clickable {
//                                    val intent =
//                                        Intent(this@RestaurantScreen, FoodScreen::class.java)
//                                    intent.putExtra("name", name1)
//                                    intent.putExtra("address", address1)
//                                    intent.putExtra("rating", rating1)
//                                    intent.putExtra("images", images1.toTypedArray())
//                                    intent.putExtra("currentPage", currentPage1)
//                                    startActivity(intent)
//                                }
//                                .padding(8.dp),
//                            elevation = CardDefaults.cardElevation(
//                                defaultElevation = 10.dp
//                            ),
//                            shape = RoundedCornerShape(16.dp)
//                        ) {
//                            Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
//                                Image(
//                                    painter = rememberImagePainter(
//                                        data = images1[currentPage1],
//                                        imageLoader = LocalImageLoader.current,
//                                        builder = {
//                                            if (true) this.crossfade(LoadPainterDefaults.FadeInTransitionDuration)
//                                            placeholder(0)
//                                        }
//                                    ),
//                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(175.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .clickable {
//                                            val intent = Intent(
//                                                this@RestaurantScreen,
//                                                FoodScreen::class.java
//                                            )
//                                            intent.putExtra("name", name1)
//                                            intent.putExtra("address", address1)
//                                            intent.putExtra("rating", rating1)
//                                            intent.putExtra("images", images1.toTypedArray())
//                                            intent.putExtra("currentPage", currentPage1)
//                                            startActivity(intent)
//                                        }
//                                )
//
//                                Spacer(modifier = Modifier.height(8.dp))
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Text(
//                                        text = name1,
//                                        style = TextStyle(
//                                            fontSize = 18.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            color = Color.Black
//                                        ),
//                                        modifier = Modifier.padding(start = 16.dp)
//                                    )
//
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                                    ) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.ic_star),
//                                            contentDescription = null,
//                                            tint = Color(0xFFFFC107),
//                                            modifier = Modifier.size(16.dp)
//                                        )
//                                        Text(
//                                            text = "Rating: $rating1",
//                                            style = TextStyle(
//                                                fontSize = 16.sp,
//                                                color = Color.Gray
//                                            )
//                                        )
//                                    }
//                                }
//
//                                Text(
//                                    text = "Address: $address1",
//                                    style = TextStyle(
//                                        fontSize = 14.sp,
//                                        color = Color.Gray
//                                    ),
//                                    modifier = Modifier.padding(16.dp, 6.dp)
//                                )
//                            }
//
//                            // Calculate delay based on the index of the restaurant
//                            val delayMillis1 = (1) * 1000L // 1000 milliseconds = 1 second
//
//                            // Auto-scroll images with a delay
//                            LaunchedEffect(currentPage1) {
//                                while (true) {
//                                    delay(delayMillis1 + 3000) // Delay before switching to the next image
//                                    currentPage1 = (currentPage1 + 1) % numImages1
//                                }
//                            }
//                        }
//                    }
//                    item {
//                        // Restaurant Card 1
//                        val name1 = "Bukhara"
//                        val address1 = "New Delhi"
//                        val rating1 = 4.75f
//                        val images1 = listOf(R.drawable.restraunt4, R.drawable.restraunt5, R.drawable.restraunt6)
//                        val numImages1 = images1.size
//                        var currentPage1 by remember { mutableStateOf(0) }
//
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(282.dp)
//                                .clickable {
//                                    val intent =
//                                        Intent(this@RestaurantScreen, FoodScreen::class.java)
//                                    intent.putExtra("name", name1)
//                                    intent.putExtra("address", address1)
//                                    intent.putExtra("rating", rating1)
//                                    intent.putExtra("images", images1.toTypedArray())
//                                    intent.putExtra("currentPage", currentPage1)
//                                    startActivity(intent)
//                                }
//                                .padding(8.dp),
//                            elevation = CardDefaults.cardElevation(
//                                defaultElevation = 10.dp
//                            ),
//                            shape = RoundedCornerShape(16.dp)
//                        ) {
//                            Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
//                                Image(
//                                    painter = rememberImagePainter(
//                                        data = images1[currentPage1],
//                                        imageLoader = LocalImageLoader.current,
//                                        builder = {
//                                            if (true) this.crossfade(LoadPainterDefaults.FadeInTransitionDuration)
//                                            placeholder(0)
//                                        }
//                                    ),
//                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(175.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .clickable {
//                                            val intent = Intent(
//                                                this@RestaurantScreen,
//                                                FoodScreen::class.java
//                                            )
//                                            intent.putExtra("name", name1)
//                                            intent.putExtra("address", address1)
//                                            intent.putExtra("rating", rating1)
//                                            intent.putExtra("images", images1.toTypedArray())
//                                            intent.putExtra("currentPage", currentPage1)
//                                            startActivity(intent)
//                                        }
//                                )
//
//                                Spacer(modifier = Modifier.height(8.dp))
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Text(
//                                        text = name1,
//                                        style = TextStyle(
//                                            fontSize = 18.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            color = Color.Black
//                                        ),
//                                        modifier = Modifier.padding(start = 16.dp)
//                                    )
//
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                                    ) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.ic_star),
//                                            contentDescription = null,
//                                            tint = Color(0xFFFFC107),
//                                            modifier = Modifier.size(16.dp)
//                                        )
//                                        Text(
//                                            text = "Rating: $rating1",
//                                            style = TextStyle(
//                                                fontSize = 16.sp,
//                                                color = Color.Gray
//                                            )
//                                        )
//                                    }
//                                }
//
//                                Text(
//                                    text = "Address: $address1",
//                                    style = TextStyle(
//                                        fontSize = 14.sp,
//                                        color = Color.Gray
//                                    ),
//                                    modifier = Modifier.padding(16.dp, 6.dp)
//                                )
//                            }
//
//                            // Calculate delay based on the index of the restaurant
//                            val delayMillis1 = (1) * 1000L // 1000 milliseconds = 1 second
//
//                            // Auto-scroll images with a delay
//                            LaunchedEffect(currentPage1) {
//                                while (true) {
//                                    delay(delayMillis1 + 4500) // Delay before switching to the next image
//                                    currentPage1 = (currentPage1 + 1) % numImages1
//                                }
//                            }
//                        }
//                    }
//                    item {
//                        // Restaurant Card 1
//                        val name1 = "Karavalli"
//                        val address1 = "Bangalore"
//                        val rating1 = 4.67f
//                        val images1 = listOf(R.drawable.restraunt7, R.drawable.restraunt8, R.drawable.restraunt9)
//                        val numImages1 = images1.size
//                        var currentPage1 by remember { mutableStateOf(0) }
//
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(282.dp)
//                                .clickable {
//                                    val intent =
//                                        Intent(this@RestaurantScreen, FoodScreen::class.java)
//                                    intent.putExtra("name", name1)
//                                    intent.putExtra("address", address1)
//                                    intent.putExtra("rating", rating1)
//                                    intent.putExtra("images", images1.toTypedArray())
//                                    intent.putExtra("currentPage", currentPage1)
//                                    startActivity(intent)
//                                }
//                                .padding(8.dp),
//                            elevation = CardDefaults.cardElevation(
//                                defaultElevation = 10.dp
//                            ),
//                            shape = RoundedCornerShape(16.dp)
//                        ) {
//                            Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
//                                Image(
//                                    painter = rememberImagePainter(
//                                        data = images1[currentPage1],
//                                        imageLoader = LocalImageLoader.current,
//                                        builder = {
//                                            if (true) this.crossfade(LoadPainterDefaults.FadeInTransitionDuration)
//                                            placeholder(0)
//                                        }
//                                    ),
//                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(175.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .clickable {
//                                            val intent = Intent(
//                                                this@RestaurantScreen,
//                                                FoodScreen::class.java
//                                            )
//                                            intent.putExtra("name", name1)
//                                            intent.putExtra("address", address1)
//                                            intent.putExtra("rating", rating1)
//                                            intent.putExtra("images", images1.toTypedArray())
//                                            intent.putExtra("currentPage", currentPage1)
//                                            startActivity(intent)
//                                        }
//                                )
//
//                                Spacer(modifier = Modifier.height(8.dp))
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Text(
//                                        text = name1,
//                                        style = TextStyle(
//                                            fontSize = 18.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            color = Color.Black
//                                        ),
//                                        modifier = Modifier.padding(start = 16.dp)
//                                    )
//
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                                    ) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.ic_star),
//                                            contentDescription = null,
//                                            tint = Color(0xFFFFC107),
//                                            modifier = Modifier.size(16.dp)
//                                        )
//                                        Text(
//                                            text = "Rating: $rating1",
//                                            style = TextStyle(
//                                                fontSize = 16.sp,
//                                                color = Color.Gray
//                                            )
//                                        )
//                                    }
//                                }
//
//                                Text(
//                                    text = "Address: $address1",
//                                    style = TextStyle(
//                                        fontSize = 14.sp,
//                                        color = Color.Gray
//                                    ),
//                                    modifier = Modifier.padding(16.dp, 6.dp)
//                                )
//                            }
//
//                            // Calculate delay based on the index of the restaurant
//                            val delayMillis1 = (1) * 1000L // 1000 milliseconds = 1 second
//
//                            // Auto-scroll images with a delay
//                            LaunchedEffect(currentPage1) {
//                                while (true) {
//                                    delay(delayMillis1 + 5500) // Delay before switching to the next image
//                                    currentPage1 = (currentPage1 + 1) % numImages1
//                                }
//                            }
//                        }
//                    }
//                    item {
//                        // Restaurant Card 1
//                        val name1 = "Gali Paranthe Wali"
//                        val address1 = "Old Delhi"
//                        val rating1 = 4.9f
//                        val images1 = listOf(R.drawable.restraunt10, R.drawable.restraunt11, R.drawable.restraunt12)
//                        val numImages1 = images1.size
//                        var currentPage1 by remember { mutableStateOf(0) }
//
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(282.dp)
//                                .clickable {
//                                    val intent =
//                                        Intent(this@RestaurantScreen, FoodScreen::class.java)
//                                    intent.putExtra("name", name1)
//                                    intent.putExtra("address", address1)
//                                    intent.putExtra("rating", rating1)
//                                    intent.putExtra("images", images1.toTypedArray())
//                                    intent.putExtra("currentPage", currentPage1)
//                                    startActivity(intent)
//                                }
//                                .padding(8.dp),
//                            elevation = CardDefaults.cardElevation(
//                                defaultElevation = 10.dp
//                            ),
//                            shape = RoundedCornerShape(16.dp)
//                        ) {
//                            Column(modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 0.dp)) {
//                                Image(
//                                    painter = rememberImagePainter(
//                                        data = images1[currentPage1],
//                                        imageLoader = LocalImageLoader.current,
//                                        builder = {
//                                            if (true) this.crossfade(LoadPainterDefaults.FadeInTransitionDuration)
//                                            placeholder(0)
//                                        }
//                                    ),
//                                    contentDescription = null,
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(175.dp)
//                                        .clip(RoundedCornerShape(8.dp))
//                                        .clickable {
//                                            val intent = Intent(
//                                                this@RestaurantScreen,
//                                                FoodScreen::class.java
//                                            )
//                                            intent.putExtra("name", name1)
//                                            intent.putExtra("address", address1)
//                                            intent.putExtra("rating", rating1)
//                                            intent.putExtra("images", images1.toTypedArray())
//                                            intent.putExtra("currentPage", currentPage1)
//                                            startActivity(intent)
//                                        }
//                                )
//
//                                Spacer(modifier = Modifier.height(8.dp))
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.SpaceBetween,
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Text(
//                                        text = name1,
//                                        style = TextStyle(
//                                            fontSize = 18.sp,
//                                            fontWeight = FontWeight.Bold,
//                                            color = Color.Black
//                                        ),
//                                        modifier = Modifier.padding(start = 16.dp)
//                                    )
//
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically,
//                                        modifier = Modifier.padding(start = 4.dp, end = 4.dp)
//                                    ) {
//                                        Icon(
//                                            painter = painterResource(id = R.drawable.ic_star),
//                                            contentDescription = null,
//                                            tint = Color(0xFFFFC107),
//                                            modifier = Modifier.size(16.dp)
//                                        )
//                                        Text(
//                                            text = "Rating: $rating1",
//                                            style = TextStyle(
//                                                fontSize = 16.sp,
//                                                color = Color.Gray
//                                            )
//                                        )
//                                    }
//                                }
//
//                                Text(
//                                    text = "Address: $address1",
//                                    style = TextStyle(
//                                        fontSize = 14.sp,
//                                        color = Color.Gray
//                                    ),
//                                    modifier = Modifier.padding(16.dp, 6.dp)
//                                )
//                            }
//
//                            // Calculate delay based on the index of the restaurant
//                            val delayMillis1 = (1) * 1000L // 1000 milliseconds = 1 second
//
//                            // Auto-scroll images with a delay
//                            LaunchedEffect(currentPage1) {
//                                while (true) {
//                                    delay(delayMillis1 + 7000) // Delay before switching to the next image
//                                    currentPage1 = (currentPage1 + 1) % numImages1
//                                }
//                            }
//                        }
//                    }
//                    // Repeat the same structure for other restaurant cards
//                }
//
//            }
//        }
//    }
//}








/*fun FoodScreenContent(
       name: String,
       address: String,
       rating: Double,
       images: IntArray,
       currentPage: Int,
       dishes: List<Dish>,
       context: Context
   ) {
       val starIconPainter: Painter = painterResource(id = R.drawable.ic_star)

       var searchText by remember { mutableStateOf("") }
       var cartItems by remember { mutableStateOf(0) }
       Log.d("my Rating", rating.toString())
       Surface(
           modifier = Modifier.fillMaxSize()
       ) {
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
           Column(
               modifier = Modifier
                   .fillMaxSize()

           ) {
               // Restaurant details
               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .clip(RoundedCornerShape(0, 0, 8, 8))
                       .background(Color(0xFFFF0000))
                       .border(2.dp, Color.Black, RoundedCornerShape(0, 0, 8, 8))
                       .height(200.dp)
               ) {

                   Column(
                       modifier = Modifier
                           .padding(16.dp)
                           .fillMaxSize()
                   ) {
                       Spacer(modifier = Modifier.height(10.dp))

                       Row(
                           modifier = Modifier.fillMaxWidth(),
                           horizontalArrangement = Arrangement.Center
                       ) {
                           Text(
                               text = name,
                               style = TextStyle(
                                   fontSize = 24.sp,
                                   fontWeight = FontWeight.Bold,
                                   color = Color.Black,
                                   textAlign = TextAlign.Center
                               ),
                               modifier = Modifier.align(Alignment.CenterVertically)
                           )
                       }

                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           modifier = Modifier.fillMaxWidth(),
                           horizontalArrangement = Arrangement.Center
                       ) {
                           Text(
                               text = address,
                               style = TextStyle(
                                   fontSize = 16.sp,
                                   textAlign = TextAlign.Center,
                                   color = Color.White
                               ),
                               modifier = Modifier.align(Alignment.CenterVertically)
                           )
                       }

                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(vertical = 8.dp),
                           horizontalArrangement = Arrangement.Center
                       ) {
                           Icon(
                               painter = starIconPainter,
                               tint = Color(0xFFFFC107),
                               contentDescription = null,
                               modifier = Modifier.size(36.dp)
                           )
                           // Add space between icon and rating
                           Text(
                               text = "$rating",
                               style = TextStyle(
                                   fontSize = 16.sp,
                                   color = Color.White
                               )
                           )

                           Spacer(modifier = Modifier.width(4.dp))
                           Text(
                               text = " | ",
                               style = TextStyle(
                                   fontSize = 16.sp,
                                   color = Color.Black
                               )
                           )
                           Spacer(modifier = Modifier.width(4.dp))
                           // Small box with veg icon
                           Box(
                               modifier = Modifier
                                   .size(24.dp)
                                   .background(Color(0xFF008000), shape = RoundedCornerShape(4.dp))
                                   .padding(4.dp),
                               contentAlignment = Alignment.Center
                           ) {
                               Icon(
                                   painter = painterResource(id = R.drawable.ic_leaf), // Replace with your veg icon
                                   contentDescription = "Veg Icon",
                                   tint = Color.White,
                                   modifier = Modifier.size(16.dp)
                               )
                           }
                       }
                       Box(
                           modifier = Modifier
                               .fillMaxWidth()
                               .background(
                                   color = Color.White,
                                   shape = RoundedCornerShape(8.dp)
                               )
                               .border(
                                   width = 1.dp,
                                   color = Color.Gray,
                                   shape = RoundedCornerShape(8.dp)
                               )
                       ) {
                           Row(
                               modifier = Modifier.fillMaxWidth(),
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               BasicTextField(
                                   value = searchText,
                                   onValueChange = { searchText = it },
                                   textStyle = TextStyle(
                                       fontSize = 18.sp,
                                       fontWeight = FontWeight.Normal
                                   ),
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .weight(1f) // Take up available space
                                       .padding(12.dp)
                               )

                               Icon(
                                   painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
                                   contentDescription = null,
                                   tint = Color.Gray,
                                   modifier = Modifier.padding(8.dp)
                               )
                           }
                       }
                   }
               }

               // Restaurant images
               if (images.isNotEmpty()) {
                   val currentImage = images[currentPage]
                   Image(
                       painter = rememberImagePainter(data = currentImage),
                       contentDescription = null,
                       contentScale = ContentScale.Crop,
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(200.dp)
                           .clip(MaterialTheme.shapes.medium)
                   )
               }

               // Search bar
               // List of dishes
               LazyColumn(
                   modifier = Modifier.fillMaxWidth(),
                   contentPadding = PaddingValues(16.dp)
               ) {
                   items(dishes) { dish ->
                       DishCard(dish = dish, context = context) {
                           cartItems++
                           // Handle add to cart button click here
                       }
                   }
               }

               // Cart items count
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(top = 16.dp),
                   horizontalArrangement = Arrangement.End
               ) {
                   Icon(
                       painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                       contentDescription = null,
                       modifier = Modifier.size(36.dp)
                   )
                   Text(
                       text = "Cart: $cartItems",
                       style = TextStyle(
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold
                       )
                   )
               }
           }
       }
   }

}
@Composable
fun DishCard(dish: Dish, context: Context, onAddToCartClick: () -> Unit) {
   Card(
       modifier = Modifier
           .fillMaxWidth()
           .clickable {
               // Handle dish card click here
           },
       elevation = CardDefaults.cardElevation(
           defaultElevation = 8.dp
       ),
       shape = MaterialTheme.shapes.medium,
   ) {
       Row(
           modifier = Modifier
               .padding(16.dp)
               .fillMaxWidth(),
       ) {
           Image(
               painter = painterResource(id = dish.imageResId),
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier
                   .width(100.dp)
                   .height(100.dp)
                   .clip(MaterialTheme.shapes.medium)
           )
           Spacer(modifier = Modifier
               .width(10.dp))
           Column {
               Text(
                   text = dish.name,
                   style = TextStyle(
                       fontSize = 18.sp,
                       fontWeight = FontWeight.Bold,
                       color = MaterialTheme.colorScheme.primary
                   )
               )
               Spacer(modifier = Modifier.height(5.dp))
               Row(
                   modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.SpaceBetween
               ) {
                   Text(
                       text = dish.cost,
                       style = TextStyle(
                           fontSize = 16.sp,
                           color = Color.Gray
                       )
                   )

                   IconButton(
                       onClick = { viewModel.addItemToCart(dish) },
                       modifier = Modifier
                           .height(36.dp)
                           .width(120.dp) // Increase the width here
                           .padding(4.dp)
                           .background(Color(0xFFFF0000), shape = RoundedCornerShape(8))
                   ) {
                       Row(
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.spacedBy(4.dp)
                       ) {
                           Icon(
                               painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
                               contentDescription = "Add to Cart",
                               tint = Color.White
                           )
                           Text(
                               text = "Add to Cart",
                               color = Color.White
                           )
                       }
                   }
               }
           }

       }
   }
   Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun FoodScreenContent(
   name: String,
   address: String,
   rating: Double,
   images: IntArray,
   currentPage: Int,
   dishes: List<Dish>,
   context: Context,
   cartViewModel: CartViewModel
) {
   // ...

   LazyColumn(
       modifier = Modifier.fillMaxWidth(),
       contentPadding = PaddingValues(16.dp)
   ) {
       items(dishes) { dish ->
           DishCard(dish = dish, context = context) {
               cartViewModel.addItemToCart(dish) // Handle add to cart button click
           }
       }
   }

   // ...

   Row(
       modifier = Modifier
           .fillMaxWidth()
           .padding(top = 16.dp),
       horizontalArrangement = Arrangement.End
   ) {
       Icon(
           painter = painterResource(id = R.drawable.baseline_add_shopping_cart_24),
           contentDescription = null,
           modifier = Modifier.size(36.dp)
       )
       Text(
           text = "Cart: ${cartViewModel.cartItems.size}",
           style = TextStyle(
               fontSize = 18.sp,
               fontWeight = FontWeight.Bold
           ),
           modifier = Modifier.clickable {
               // Navigate to the Cart Activity when the cart icon is clicked
               context.startActivity(Intent(context, com.example.assignment4foodapp.CartActivity::class.java))
           }
       )
   }
}*/