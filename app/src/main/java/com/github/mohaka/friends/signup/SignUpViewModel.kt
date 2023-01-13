package com.github.mohaka.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.domain.validation.CredentialsValidationResult
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.state.SignUpState

class SignUpViewModel(private val credentialsValidator: RegexCredentialsValidator) {
	private val _mutableSignUpState = MutableLiveData<SignUpState>()
	val signUpState: LiveData<SignUpState> = _mutableSignUpState
	val userRepository = UserRepository(InMemoryUserCatalog())

	fun createAccount(email: String, password: String, about: String) {
		val result = credentialsValidator.validate(email, password)

		_mutableSignUpState.value = when (result) {
			CredentialsValidationResult.InvalidEmail -> SignUpState.BadEmail
			CredentialsValidationResult.InvalidPassword -> SignUpState.BadPassword
			CredentialsValidationResult.Valid -> userRepository.signUp(email, password, about)
		}
	}
}

class UserRepository(private val userCatalog: InMemoryUserCatalog) {
	public fun signUp(email: String, password: String, about: String) = try {
		val user = userCatalog.createUser(email, password, about)
		SignUpState.SignedUp(user)
	} catch (e: DuplicateAccountException) {
		SignUpState.DuplicateAccount
	}
}

class InMemoryUserCatalog(private val users: ArrayList<User> = arrayListOf()) {
	fun createUser(email: String, password: String, about: String): User {
		checkAccountDuplication(email)
		val userId = generateUuidFor(email)
		val user = User(userId, email, about)
		users.add(user)

		return user
	}

	private fun generateUuidFor(email: String) = ":" + email.takeWhile { it != '@' } + "Id:"

	private fun checkAccountDuplication(email: String) {
		if (users.any { it.email == email })
			throw DuplicateAccountException()
	}
}

class DuplicateAccountException : Throwable()
