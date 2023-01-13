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
class CreateAccountTest {
	private val viewModel = SignUpViewModel(
		RegexCredentialsValidator(),
		UserRepository(InMemoryUserCatalog())
	)

	@Test
	fun accountCreated() {
		val maya = User(":mayaId:", "maya@friends.com", "about Maya")
		viewModel.createAccount(maya.email, "MaYa@2021", maya.about)
		assertEquals(SignUpState.SignedUp(maya), viewModel.signUpState.value)
	}

	@Test
	fun anotherAccountCreated() {
		val bob = User(":bobId:", "bob@friends.com", "about Bob");
		viewModel.createAccount(bob.email, "BoB@2022", bob.about)
		assertEquals(SignUpState.SignedUp(bob), viewModel.signUpState.value)
	}

	@Test
	fun createDuplicateAccount() {
		val anna = User(":annaId:", "anna@friens.com", "about Anna")
		viewModel.also {
			it.createAccount(anna.email, "aNnA!2001", anna.about)
		}
		viewModel.createAccount(anna.email, "AnNa@2022", anna.about)
		assertEquals(SignUpState.DuplicateAccount, viewModel.signUpState.value)
	}
}