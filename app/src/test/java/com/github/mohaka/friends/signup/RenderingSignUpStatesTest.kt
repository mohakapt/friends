package com.github.mohaka.friends.signup

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class RenderingSignUpStatesTest {

	private val userRepository = UserRepository(InMemoryUserCatalog())
	private val viewModel = SignUpViewModel(RegexCredentialsValidator(), userRepository)
	private val tom = User(":tomId:", "tom@friends.com", "about tom")

	@Test
	fun uiStatesAreDeliveredInParticularOrder() {
		val deliveredStates = mutableListOf<SignUpState>()
		viewModel.signUpState.observeForever { deliveredStates.add(it) }

		viewModel.createAccount(tom.email, "P@ssw0rd**", tom.about)

		assertEquals(
			listOf(SignUpState.Loading, SignUpState.SignedUp(tom)),
			deliveredStates
		)
	}
}