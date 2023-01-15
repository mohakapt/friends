package com.github.mohaka.friends.signup

import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.NetworkException
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.user.UserCatalog
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

	@Test
	fun backendError() {
		val userRepository = UserRepository(UnavailableUserCatalog())
		val result = userRepository.signUp(":email:", ":password:", ":about:")
		assertEquals(SignUpState.BackendError, result)
	}

	@Test
	fun offlineError() {
		val userRepository = UserRepository(OfflineUserCatalog())
		val result = userRepository.signUp(":email:", ":password:", ":about:")
		assertEquals(SignUpState.OfflineError, result)
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
}