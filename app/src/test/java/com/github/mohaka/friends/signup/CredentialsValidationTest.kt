package com.github.mohaka.friends.signup

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.TestDispatchers
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.domain.validation.CredentialsValidationResult
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.state.SignUpState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@ExtendWith(InstantTaskExecuteExtension::class)
class CredentialsValidationTest {

	private val viewModel = SignUpViewModel(
		RegexCredentialsValidator(),
		UserRepository(InMemoryUserCatalog()),
		TestDispatchers(),
	)

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
		viewModel.createAccount(email, ":password:", ":about:")
		assertEquals(SignUpState.BadEmail, viewModel.signUpState.value)
	}

	@ParameterizedTest
	@CsvSource(
		"''",
		"'             '",
		"'12345678'",
		"'abcd5678'",
		"'abcdef78#$'",
		"'ABCDEF78#$'",
	)
	fun invalidPassword(password: String) {
		viewModel.createAccount("example@domain.com", password, ":about:")
		assertEquals(SignUpState.BadPassword, viewModel.signUpState.value)
	}

	@Test
	fun validCredentials() {
		val credentialsValidator = RegexCredentialsValidator()
		val result = credentialsValidator.validate("example@domain.com", "12ABcd4!@")
		assertEquals(CredentialsValidationResult.Valid, result)
	}
}