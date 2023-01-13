package com.github.mohaka.friends.signup

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class CreateAccountTest {
	@Test
	fun accountCreated() {
		val viewModel = SignUpViewModel(RegexCredentialsValidator())
		viewModel.createAccount("maya@friends.com", "MaYa@2021", "about Maya")
		val maya = User(":mayaId:", "maya@friends.com", "about Maya")
		assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
	}
}