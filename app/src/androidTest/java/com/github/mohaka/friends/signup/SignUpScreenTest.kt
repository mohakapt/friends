package com.github.mohaka.friends.signup

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.mohaka.friends.MainActivity
import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.NetworkException
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.user.UserCatalog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class SignUpScreenTest {

	@get:Rule
	val signUpTestRule = createAndroidComposeRule<MainActivity>()

	@Before
	fun setup() {
		replaceUserCatalogWith(InMemoryUserCatalog())
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
			submit()
		} verify {
			badEmailErrorIsPresent()
		}
	}

	@Test
	fun displayBadPasswordError() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("heysem@friends.com")
			typePassword("123456")
			submit()
		} verify {
			badPasswordErrorIsPresent()
		}
	}

	@Test
	fun displayDuplicateAccountError() = runBlocking<Unit> {
		val signedUpEmail = "alice@friends.com"
		val signedUpPassword = "P@ssw0rd*"

		replaceUserCatalogWith(InMemoryUserCatalog().apply {
			createUser(signedUpEmail, signedUpPassword, ":about:")
		})

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

	@Test
	fun resetBadEmailError() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("heysem")
			submit()
			typeEmail("@friends.com")
		} verify {
			badEmailErrorIsNotPresent()
		}
	}

	@Test
	fun resetBadPasswordError() {
		launchSignUpScreen(signUpTestRule) {
			typeEmail("heysem@friends.com")
			typePassword("P@ss")
			submit()
			typePassword("w0rd")
		} verify {
			badPasswordErrorIsNotPresent()
		}
	}

	@Test
	fun displayBlockingLoading() {
		replaceUserCatalogWith(DelayingUserCatalog())
		launchSignUpScreen(signUpTestRule) {
			typeEmail("max@friends.com")
			typePassword("P@ssw0rd")
			submit()
		} verify {
			blockingLoadingIsPresent()
		}
	}

	@After
	fun tearDown() {
		replaceUserCatalogWith(InMemoryUserCatalog())
	}

	class UnavailableUserCatalog : UserCatalog {
		override suspend fun createUser(email: String, password: String, about: String): User {
			throw BackendException()
		}
	}

	class OfflineUserCatalog : UserCatalog {
		override suspend fun createUser(email: String, password: String, about: String): User {
			throw NetworkException()
		}
	}

	class DelayingUserCatalog : UserCatalog {
		override suspend fun createUser(email: String, password: String, about: String): User {
			delay(1000)
			return User("someId", email, about)
		}
	}

	private fun replaceUserCatalogWith(userCatalog: UserCatalog) {
		val replaceModule = module { factory { userCatalog } }
		loadKoinModules(replaceModule)
	}
}