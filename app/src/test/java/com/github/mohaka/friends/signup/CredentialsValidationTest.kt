package com.github.mohaka.friends.signup

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecuteExtension::class)
class CredentialsValidationTest {

	@ParameterizedTest
	@CsvSource(
		"'email'",
		"'a@b.c'",
		"'ab@b.c'",
		"'a@bc.c'",
		"'ab@bc.c'",
		"'ab@bc'",
		"'ab.bc'",
		"''",
		"'       '",
	)
	fun invalidEmail(email: String) {
		val viewModel = SignUpViewModel()
		viewModel.createAccount(email, ":password:", ":about:")
		assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
	}
}