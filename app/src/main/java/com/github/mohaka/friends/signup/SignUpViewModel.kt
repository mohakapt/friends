package com.github.mohaka.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.domain.validation.CredentialsValidationResult
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.state.SignUpState

class SignUpViewModel(
	private val credentialsValidator: RegexCredentialsValidator,
	private val userRepository: UserRepository,
) : ViewModel() {
	private val _mutableSignUpState = MutableLiveData<SignUpState>()
	val signUpState: LiveData<SignUpState> = _mutableSignUpState

	fun createAccount(email: String, password: String, about: String) {
		when (credentialsValidator.validate(email, password)) {
			CredentialsValidationResult.InvalidEmail -> _mutableSignUpState.value = SignUpState.BadEmail
			CredentialsValidationResult.InvalidPassword -> _mutableSignUpState.value = SignUpState.BadPassword
			CredentialsValidationResult.Valid -> proceedWithSignUp(email, password, about)
		}
	}

	private fun proceedWithSignUp(email: String, password: String, about: String) {
		_mutableSignUpState.value = SignUpState.Loading
		_mutableSignUpState.value = userRepository.signUp(email, password, about)
	}
}

