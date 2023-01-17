package com.github.mohaka.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohaka.friends.CoroutineDispatchers
import com.github.mohaka.friends.domain.user.UserRepository
import com.github.mohaka.friends.domain.validation.CredentialsValidationResult
import com.github.mohaka.friends.domain.validation.RegexCredentialsValidator
import com.github.mohaka.friends.signup.state.SignUpState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
	private val credentialsValidator: RegexCredentialsValidator,
	private val userRepository: UserRepository,
	private val dispatchers: CoroutineDispatchers,
) : ViewModel() {
	private val mutableSignUpState = MutableLiveData<SignUpState>()
	val signUpState: LiveData<SignUpState> = mutableSignUpState

	fun createAccount(email: String, password: String, about: String) {
		when (credentialsValidator.validate(email, password)) {
			CredentialsValidationResult.InvalidEmail -> mutableSignUpState.value = SignUpState.BadEmail
			CredentialsValidationResult.InvalidPassword -> mutableSignUpState.value = SignUpState.BadPassword
			CredentialsValidationResult.Valid -> proceedWithSignUp(email, password, about)
		}
	}

	private fun proceedWithSignUp(email: String, password: String, about: String) {
		viewModelScope.launch {
			mutableSignUpState.value = SignUpState.Loading
			val state = withContext(dispatchers.background) {
				userRepository.signUp(email, password, about)
			}
			mutableSignUpState.value = state
		}
	}
}

