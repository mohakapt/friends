package com.github.mohaka.friends

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.github.mohaka.friends.ui.theme.FriendsTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			FriendsTheme {
					Greeting("Android")
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
				}
			}
		}
	}
}

@Composable
fun Greeting(name: String) {
	Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	FriendsTheme {
		Greeting("Android")
	}
}