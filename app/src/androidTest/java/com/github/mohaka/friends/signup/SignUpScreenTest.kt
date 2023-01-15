package com.github.mohaka.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.mohaka.friends.MainActivity
import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.NetworkException
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.user.UserCatalog
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
	private val signUpModule = module { factory<UserCatalog> { userCatalog } }

	@Before
	fun setup() {
		loadKoinModules(signUpModule)
	}

	@Test
	fun performSignUp() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("mohakapt@gmail.com")
			typePassword("P@ssw0rd*")
			submit()
		} verify {
			timelineScreenIsPresent()
		}
	}

	@Test
	fun displayBadEmailError() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("badEmail")
			typePassword("P@ssw0rd*")
			submit()
		} verify {
			badEmailErrorIsPresent()
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

	@Test
	fun displayBackendError() {
		replaceUserCatalogWith(UnavailableUserCatalog())

		launchSignUpScreen(signUpTestRule) {
			typeEmail("mike@friends.com")
			typePassword("P@ssw0rd*")
			submit()
		} verify {
			backendErrorIsPresent()
		}
	}

	@Test
	fun displayOfflineError() {
		replaceUserCatalogWith(OfflineUserCatalog())

		launchSignUpScreen(signUpTestRule) {
			typeEmail("joe@friends.com")
			typePassword("P@ssw0rd*")
			submit()
		} verify {
			offlineErrorIsPresent()
		}
	}

	@After
	fun tearDown() {
		val resetModule = module { single<UserCatalog> { InMemoryUserCatalog() } }
		loadKoinModules(resetModule)
	}

	class UnavailableUserCatalog : UserCatalog {
		override fun createUser(email: String, password: String, about: String): User {
			throw BackendException()
		}
	}

	class OfflineUserCatalog : UserCatalog {
		override fun createUser(email: String, password: String, about: String): User {
			throw NetworkException()
		}
	}

	private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
		val replaceModule = module { factory { userCatalog } }
		loadKoinModules(replaceModule)
	}

	private fun createUserWith(signedUpEmail: String, signedUpPassword: String) {
		userCatalog.createUser(signedUpEmail, signedUpPassword, ":about:")
	}
}