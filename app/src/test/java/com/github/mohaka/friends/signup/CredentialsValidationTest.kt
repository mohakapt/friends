package com.github.mohaka.friends.signup

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class CredentialsValidationTest {
	@Test
	fun invalidEmail() {
		val viewModel = SignUpViewModel()
		viewModel.createAccount("email", ":password:", ":about:")
		assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
	}
}