package com.example.assignment4foodapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.Image
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString

class MainActivity : ComponentActivity() {
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Create a mutable state for email and password fields
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }
            var isLoggedIn by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_1),
                    contentDescription = "backgroundImage",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds // Adjust contentScale as needed
                )
                Surface(
                    modifier = Modifier
                        .padding(top = 16.dp) // Adjust padding as needed
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 0.dp, bottomEnd = 0.dp)),
                    color = Color(0xFFD32F2F)
                ){
                    Column(
//                        modifier = Modifier
//                            .background(Color(0xFFD32F2F))
                    ) {
                        // Add some space at the top
                        Spacer(modifier = Modifier.height(10.dp))

                        // Title and Subtitle
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp,0.dp,15.dp,0.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Zomato",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 60.sp
                                )
                            )
                            Text(
                                text = "An app to satisfy your cravings",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        // Email Field
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp,0.dp,15.dp,0.dp),
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "Email", style = TextStyle(color = Color.White, fontSize = 18.sp)) },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                textColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )

                        // Password Field
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp,10.dp,15.dp,10.dp),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password", style = TextStyle(color = Color.White, fontSize = 18.sp)) },
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 18.sp),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val icon = if (passwordVisible) R.drawable.baseline_remove_red_eye_24 else R.drawable.baseline_remove_red_eye_24
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Image(
                                        painter = painterResource(icon),
                                        contentDescription = "Toggle password visibility"
                                    )
                                }
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                textColor = Color.White,
                                cursorColor = Color.Black
                            )
                        )

                        // Login Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                ),
                                onClick = {
                                    if (email.isBlank() || password.isBlank()) {
                                        // Display a toast message if email or password is empty
                                        showToast("Please enter credentials properly")
                                    } else {
                                        val intent = Intent(this@MainActivity, RestaurantScreen::class.java)
                                        startActivity(intent)
//                                // Handle authentication
//                                val isUserAuthenticated = authenticateUser(email, password)
//                                if (isUserAuthenticated) {
//                                    isLoggedIn = true
//                                    // Navigate to the dashboard or another screen
//                                    showToast("Logged in!")
//                                } else {
//                                    // Handle authentication failure (e.g., show an error message)
//                                    showToast("Authentication failed. Please check your credentials.")
//                                }
                                    }
                                }
                            ) {
                                Text(text = "Login")
                            }
                        }
                        Row( modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp,0.dp,10.dp,20.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
//
                            ClickableText(
                                text = AnnotatedString("New User ?"),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 22.sp,

                                ),
                                onClick = { offset ->
                                    // Start the SignupPageActivity when the "Sign-up" button is clicked
                                    val intent = Intent(this@MainActivity, SignupPageActivity::class.java)
                                    startActivity(intent)
                                    // Handle the click event here
                                    // You can navigate to the sign-up screen or perform any other action
                                }
                            )
                            ClickableText(
                                text = AnnotatedString(" Tap here"),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                onClick = { offset ->
                                    // Start the SignupPageActivity when the "Sign-up" button is clicked
                                    val intent =
                                        Intent(this@MainActivity, SignupPageActivity::class.java)
                                    startActivity(intent)
                                    // Handle the click event here
                                    // You can navigate to the sign-up screen or perform any other action
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            // Root column with a background color
        }
    }
    // Replace this function with your actual user authentication logic
    private fun authenticateUser(email: String, password: String): Boolean {
        // Implement your authentication logic here
        // Return true if authentication is successful, otherwise return false
        return (email == "user@example.com" && password == "password123")
    }
}
