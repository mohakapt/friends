package com.github.mohaka.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.mohaka.friends.signup.SignUpScreen
import com.github.mohaka.friends.signup.SignUpViewModel
import com.github.mohaka.friends.timeline.TimelineScreen
import com.github.mohaka.friends.ui.theme.FriendsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

	private val signUpViewModel: SignUpViewModel by viewModel()

	private companion object {
		private const val SIGN_UP = "signUp"
		private const val TIMELINE = "timeline"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			FriendsTheme {
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
					NavHost(navController = navController, startDestination = SIGN_UP) {
						composable(SIGN_UP) {
							SignUpScreen(
								viewModel = signUpViewModel,
								onSignedUp = { navController.navigate(TIMELINE) }
							)
						}
						composable(TIMELINE) {
							TimelineScreen()
						}
					}
				}
			}
		}
	}
}