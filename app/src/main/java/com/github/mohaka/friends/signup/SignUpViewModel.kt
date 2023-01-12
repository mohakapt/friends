package com.github.mohaka.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mohaka.friends.signup.state.SignUpState
import java.util.regex.Pattern

class SignUpViewModel {
	private val _mutableSignUpState = MutableLiveData<SignUpState>()
	val signUpState: LiveData<SignUpState> = _mutableSignUpState

	fun createAccount(email: String, password: String, about: String) {
		val result = RegexCredentialsValidator().validateCredentials(email, password)

		_mutableSignUpState.value = when (result) {
			CredentialsValidationResult.InvalidEmail -> SignUpState.BadEmail
			CredentialsValidationResult.InvalidPassword -> SignUpState.BadPassword
		}
	}

	class RegexCredentialsValidator {
		fun validateCredentials(email: String, password: String): CredentialsValidationResult {
			val emailRegex = """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""
			val passwordRegex = """""${'"'}^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#${'$'}%^&*+=\-]).{8,}${'$'}""${'"'}"""

			val result = when {
				!Pattern.compile(emailRegex).matcher(email).matches() -> CredentialsValidationResult.InvalidEmail
				!Pattern.compile(passwordRegex).matcher(password).matches() -> CredentialsValidationResult.InvalidPassword
				else -> TODO()
			}
			return result
		}
	}
}

sealed class CredentialsValidationResult {
	object InvalidEmail : CredentialsValidationResult()
	object InvalidPassword : CredentialsValidationResult()
}
