package com.github.mohaka.friends.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mohaka.friends.signup.state.SignUpState
import java.util.regex.Pattern

class SignUpViewModel {
	private val _mutableSignUpState = MutableLiveData<SignUpState>()
	val signUpState: LiveData<SignUpState> = _mutableSignUpState

	fun createAccount(email: String, password: String, about: String) {
		val emailRegex = """[a-zA-Z0-9+._%\-]{1,64}@[a-zA-Z0-9][a-zA-Z0-9\-]{1,64}(\.[a-zA-Z0-9][a-zA-Z0-9\-]{1,25})"""
		val passwordRegex = """""${'"'}^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#${'$'}%^&*+=\-]).{8,}${'$'}""${'"'}"""

		if (!Pattern.compile(emailRegex).matcher(email).matches()) {
			_mutableSignUpState.value = SignUpState.BadEmail
		} else if (!Pattern.compile(passwordRegex).matcher(password).matches()) {
			_mutableSignUpState.value = SignUpState.BadPassword
		}
	}

}
