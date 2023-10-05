package com.example.assignment4foodapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

data class Dish(
    val name: String,
    val cost: String,
    val imageResId: Int
)

class FoodScreen : ComponentActivity() {
    private val dishes = listOf(
        Dish("Butter Chicken", "\u20B910", R.drawable.restraunt1),
        Dish("Paneer Tikka", "\u20B912", R.drawable.restraunt2),
        Dish("Chole Bhature", "\u20B915", R.drawable.restraunt3),
        Dish("Masala Dosa", "\u20B98", R.drawable.restraunt4),
        Dish("Samosa", "\u20B918", R.drawable.restraunt5),
        Dish("Biryani", "\u20B911", R.drawable.restraunt6),
        Dish("Tandoori Chicken", "\u20B914", R.drawable.restraunt7),
        Dish("Aloo Paratha", "\u20B99", R.drawable.restraunt8)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val name = intent.getStringExtra("name") ?: ""
            val address = intent.getStringExtra("address") ?: ""
            val rating = intent.getFloatExtra("rating", 0.0f)
            val images = intent.getIntArrayExtra("images") ?: intArrayOf()
            val currentPage = intent.getIntExtra("currentPage", 0)

            FoodScreenContent(
                name = name,
                address = address,
                rating = rating,
                images = images,
                currentPage = currentPage,
                dishes = dishes,
                context = this
            )
        }
    }

    @Composable
    fun FoodScreenContent(
        name: String,
        address: String,
        rating: Float,
        images: IntArray,
        currentPage: Int,
        dishes: List<Dish>,
        context: Context
    ) {
        val starIconPainter: Painter = painterResource(id = R.drawable.ic_star)

        var searchText by remember { mutableStateOf("") }
        var cartItems by remember { mutableStateOf(0) }

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
                                text = " $rating",
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
                        onClick = { onAddToCartClick() },
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
