package com.example.assignment4foodapp

import CActivity
import CartActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment4foodapp.ui.theme.ExploreButton
import com.example.assignment4foodapp.ui.theme.orange
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileScreen : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            //  navController: NavController
            Surface(modifier = Modifier.fillMaxSize())
            {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight())
                {
                    Row(modifier = Modifier.fillMaxWidth())
                    {
                        IconButton(onClick = { })
                        {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(top = 10.dp)
                                    .padding(start = 10.dp)
                            )

                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "My Profile",modifier = Modifier.padding(top = 13.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }


                    Card(modifier = Modifier
                        .height(200.dp)
                        .padding(top = 30.dp)
                        .padding(horizontal = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White)
                        ,elevation = CardDefaults.cardElevation(
                            defaultElevation = 15.dp))
                    {
                        Row (modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp)
                            .padding(start = 10.dp))
                        {
                            Card(modifier = Modifier
                                .height(100.dp)
                                .width(100.dp), shape = CircleShape)
                            {
                                Image(
                                    painter = painterResource(id = R.drawable.profile_photo),
                                    contentDescription = null
                                )
                            }
                            Column{
                                Text(text = "Aryan Raj",modifier = Modifier
                                    .padding(top = 10.dp)
                                    .padding(start = 25.dp), fontWeight = FontWeight.ExtraBold, fontSize = 25.sp, color = orange)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "aryannraj0410@gmail.com",color = Color.Blue,modifier = Modifier.padding(start = 25.dp))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "One cannot think well, love well, sleep well, if one has not dined well \uD83D\uDE01. - Virginia Wool")
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Card(modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 45.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White)
                        ,elevation = CardDefaults.cardElevation(
                            defaultElevation = 15.dp))
                    {
                        Column(modifier = Modifier.padding(20.dp))
                        {
                            Row {
                                IconButton(onClick = {
//                            navController.navigate("Order")
                                }, ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.history_tab), modifier = Modifier.size(22.dp),
                                        contentDescription = null
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Order History",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 9.dp))
                            }

                            Divider(color = orange, modifier = Modifier
                                .padding(5.dp)
                                .padding(horizontal = 15.dp))

                            Row {
                                IconButton(onClick = {
                                    val intent = Intent(
                                        this@ProfileScreen,
                                        CActivity::class.java
                                    )
                                    startActivity(intent)
//
                                }, ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.cart_icon), modifier = Modifier.size(22.dp),
                                        contentDescription = null
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Cart Activity",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 9.dp))
                            }

                            Divider(color = orange, modifier = Modifier
                                .padding(5.dp)
                                .padding(horizontal = 15.dp))

                            Row {
                                IconButton(onClick = {
//                            navController.navigate("Order")
                                }, ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.fav_tab), modifier = Modifier.size(22.dp),
                                        contentDescription = null
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Wishlist",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 9.dp))
                            }

                            Divider(color = orange, modifier = Modifier
                                .padding(5.dp)
                                .padding(horizontal = 15.dp))
                            Row {
                                IconButton(onClick = {
//                            navController.navigate("Order")
                                }, ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logout),
                                        contentDescription = "Logout Icon",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    try {
                                                        Firebase.auth.signOut()
                                                        withContext(Dispatchers.Main) {
                                                            Toast
                                                                .makeText(
                                                                    this@ProfileScreen,
                                                                    "Sign-out successful",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                            val intent = Intent(
                                                                this@ProfileScreen,
                                                                SignupPageActivity::class.java
                                                            )
                                                            startActivity(intent)
                                                        }
                                                    } catch (e: Exception) {
                                                        e.printStackTrace()
                                                    }
                                                }
                                            }
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "LogOut",
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 9.dp))
                            }


                        }

                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Card(modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White)
                        ,elevation = CardDefaults.cardElevation(
                            defaultElevation = 15.dp))
                    {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            Column (modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Image(painter = painterResource(id = R.drawable.card), contentDescription =null )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = "Card")

                            }
                            Column(modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Image(painter = painterResource(id = R.drawable.bank), contentDescription =null )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = "Bank")

                            }
                            Column(modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally)
                            {
                                Image(painter = painterResource(id = R.drawable.paypal), contentDescription =null )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = "PayPal")

                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            ExploreButton(text = "UPDATE")
                        }
                    }

                }


            }

        }
    }
}