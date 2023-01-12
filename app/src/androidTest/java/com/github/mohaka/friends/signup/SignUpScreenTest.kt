package com.github.mohaka.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.mohaka.friends.MainActivity
import org.junit.Rule
import org.junit.Test

class SignUpScreenTest {

	@get:Rule
	val signUpTestRule = createAndroidComposeRule<MainActivity>()

	@Test
	fun performSignUp() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("mohakapt@gmail.com")
			typePassword("P@ssw0rd")
			submit()
		} verify {
			timelineScreenIsPresent()
		}
	}
}