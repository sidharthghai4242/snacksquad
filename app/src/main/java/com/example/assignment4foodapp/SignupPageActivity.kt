package com.example.assignment4foodapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.remember
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import androidx.lifecycle.viewmodel.compose.viewModel

class SignupPageActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private fun saveUserDataLocally(userData: UserModel) {
        sharedPreferences.edit {
            putString("userId", userData.userId)
            print(userData.userId)
            putString("username", userData.username)
            print(userData.username)
            putString("email", userData.email)
            print(userData.email)
            // You can add more data as needed
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        // Create or retrieve the SharedViewModel instance
        val sharedViewModel: SharedViewModel by viewModels() // Add this to your Activity
        lateinit var auth: FirebaseAuth
        auth = Firebase.auth
        setContent {
            // Create a mutable state for sign-up fields
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var phoneno by remember{ mutableStateOf("") }
            var address by remember{ mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }
            var registrationInProgress by remember { mutableStateOf(false) }

            // Root column with a background color
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD32F2F))
                    .padding(16.dp)
            ) {
                item{
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
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = phoneno,
                        onValueChange = { phoneno = it },
                        label = { Text(text = "Phone number", style = TextStyle(color = Color.White, fontSize = 18.sp)) },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 18.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            textColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = address,
                        onValueChange = { address = it },
                        label = { Text(text = "Address", style = TextStyle(color = Color.White, fontSize = 18.sp)) },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 18.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            textColor = Color.White,
                            cursorColor = Color.Black
                        )
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        onClick = {
                            // ... (other validation logic)
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this@SignupPageActivity) { task ->
                                    if (task.isSuccessful) {
                                        // Sign-up success, update UI with the signed-up user's information
                                        val user = auth.currentUser
                                        showToast("Sign-up successful: ${user?.email}")

                                        // Create a Firestore reference to the users collection
                                        val db = Firebase.firestore
                                        val usersCollection = db.collection("users")

                                        // Generate a new unique userId
                                        val userId = usersCollection.document().id

                                        // Create a new user document with username, email, and userId fields
                                        val userData = hashMapOf(
                                            "username" to username,
                                            "email" to email,
                                            "cart" to emptyList<Any>(), // Initialize the cart as an empty list
                                            "userId" to userId // Add the userId field
                                        )

                                        // Add the user document to the Firestore collection
                                        usersCollection
                                            .document(user?.uid ?: "") // Use the UID as the document ID
                                            .set(userData)
                                            .addOnSuccessListener {
                                                // Document creation success
                                                showToast("User data added to Firestore")

                                                // Create a UserModel with the updated fields
                                                val newUserModel = UserModel(
                                                    username = username,
                                                    email = email,
                                                    userId = userId,
                                                    cart = emptyList() // Initialize the cart as an empty list
                                                )

                                                // Store the UserModel in the shared ViewModel
                                                sharedViewModel.setUserModel(newUserModel)
                                                val um = sharedViewModel.getUserModel()
                                                Log.d("myTag", um.toString());
                                                Log.d("myTag", newUserModel.toString())
                                                print("user model set")
                                                // Save UserData locally using SharedPreferences
                                                saveUserDataLocally(newUserModel)

                                                // Implement any additional actions here, such as navigating to a new screen
                                                val intent = Intent(this@SignupPageActivity, RestaurantScreen::class.java)
                                                startActivity(intent)
                                            }
                                            .addOnFailureListener { e ->
                                                // If document creation fails, display a message to the user.
                                                showToast("Error adding user data to Firestore: ${e.message}")
                                                Log.d("myTag","Error adding user data to Firestore: ${e.message}")
                                            }
                                    } else {
                                        // If sign-up fails, display a message to the user.
                                        showToast("Sign-up failed. ${task.exception?.message}")
                                    }
                                }
                        },
                        enabled = !registrationInProgress
                    ) {
                        // Show different content based on login in progress
                        if (registrationInProgress) {
                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text(text = "Sign-Up")
                        }
                    }
                }
            }
        }
    }
}
//val userId = sharedPreferences.getString("userId", "")
//val username = sharedPreferences.getString("username", "")
//val email = sharedPreferences.getString("email", "")