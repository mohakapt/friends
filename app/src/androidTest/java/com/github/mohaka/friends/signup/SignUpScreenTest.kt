package com.github.mohaka.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.mohaka.friends.MainActivity
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

	@get:Rule
	val signUpTestRule = createAndroidComposeRule<MainActivity>()

	private val userCatalog = InMemoryUserCatalog()
	private val signUpModule = module { factory { userCatalog } }

	@Before
	fun setup() {
		loadKoinModules(signUpModule)
	}

	@After
	fun tearDown() {
		val resetModule = module { single { InMemoryUserCatalog() } }
		loadKoinModules(resetModule)
	}

	@Test
	fun performSignUp() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("mohakapt@gmail.com")
			typePassword("P@sSw0rd123**")
			submit()
		} verify {
			timelineScreenIsPresent()
		}
	}

	@Test
	fun displayDuplicateAccountError() {
		val signedUpEmail = "alice@friends.com"
		val signedUpPassword = "P@ssw0rd*"
		createUserWith(signedUpEmail, signedUpPassword)

		launchSignUpScreen(signUpTestRule) {
			typeEmail(signedUpEmail)
			typePassword(signedUpPassword)
			submit()
		} verify {
			duplicateAccountErrorIsPresent()
		}
	}

	private fun createUserWith(signedUpEmail: String, signedUpPassword: String) {
		userCatalog.createUser(signedUpEmail, signedUpPassword, ":about:")
	}
}