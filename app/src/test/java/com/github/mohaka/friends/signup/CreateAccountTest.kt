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
		val maya = User(":mayaId:", "maya@friends.com", "about Maya")
		val viewModel = SignUpViewModel(RegexCredentialsValidator())
		viewModel.createAccount(maya.email, "MaYa@2021", maya.about)
		assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
	}
}