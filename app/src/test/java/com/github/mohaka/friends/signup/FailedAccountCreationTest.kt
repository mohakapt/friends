package com.github.mohaka.friends.signup

import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.ConnectionException
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.user.UserCatalog
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.signup.state.SignUpState
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FailedAccountCreationTest {

	@Test
	fun backendError() = runBlocking {
		val userRepository = UserRepository(UnavailableUserCatalog())
		val result = userRepository.signUp(":email:", ":password:", ":about:")
		assertEquals(SignUpState.BackendError, result)
	}

	@Test
	fun offlineError() = runBlocking {
		val userRepository = UserRepository(OfflineUserCatalog())
		val result = userRepository.signUp(":email:", ":password:", ":about:")
		assertEquals(SignUpState.OfflineError, result)
	}

	class UnavailableUserCatalog : UserCatalog {
		override suspend fun createUser(email: String, password: String, about: String): User {
			throw BackendException()
		}

		override suspend fun followedBy(userUuid: String): List<String> {
			TODO("Not yet implemented")
		}
	}

	class OfflineUserCatalog : UserCatalog {
		override suspend fun createUser(email: String, password: String, about: String): User {
			throw ConnectionException()
		}

		override suspend fun followedBy(userUuid: String): List<String> {
			TODO("Not yet implemented")
		}
	}
}