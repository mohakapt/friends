package com.github.mohaka.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.domain.user.UserRepository
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

