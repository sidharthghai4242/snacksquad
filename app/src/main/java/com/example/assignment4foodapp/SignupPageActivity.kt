package com.example.assignment4foodapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SignupPageActivity : ComponentActivity() {
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Create a mutable state for sign-up fields
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }

            // Root column with a background color
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD32F2F))
                    .padding(16.dp)
            ) {
                // Title
                Text(
                    text = "Sign Up",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )

                // Username Field
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Username", style = TextStyle(color = Color.White, fontSize = 18.sp)) },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 18.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        textColor = Color.White,
                        cursorColor = Color.Black
                    )
                )

                // Email Field
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
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
                        .padding(16.dp),
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
                // Sign-up Button
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    onClick = {
                        if (email.isEmpty()) {
                            showToast("Please enter your email")
                        } else if (password.isEmpty()) {
                            showToast("Please enter your password")
                        } else if (username.isEmpty()) {
                            showToast("Please enter your username")
                        } else {
                            // Start the SignupPageActivity when the "Sign-up" button is clicked
                            val intent = Intent(this@SignupPageActivity, RestaurantScreen::class.java)
                            startActivity(intent)
                            // Implement your sign-up logic here
                        }
                    }
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}
