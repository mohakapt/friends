package com.github.mohaka.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.mohaka.friends.signup.SignUpScreen
import com.github.mohaka.friends.ui.theme.FriendsTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			FriendsTheme {
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
					NavHost(navController = navController, startDestination = "signUp") {
						composable("signUp") {
							SignUpScreen(onSignedUp = { navController.navigate("timeline") })
						}
						composable("timeline") {
							Text(text = stringResource(R.string.title_timeline))
						}
					}
				}
			}
		}
	}
}