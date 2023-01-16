package com.github.mohaka.friends.signup.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpScreenState(
	private val coroutineScope: CoroutineScope
) {
	var email by mutableStateOf("")
	var password by mutableStateOf("")
	var about by mutableStateOf("")

	var isEmailErrorVisible by mutableStateOf(false)
	var isPasswordErrorVisible by mutableStateOf(false)

	var reportedSignedUp by mutableStateOf(false)

	var isErrorVisible by mutableStateOf(false)
	var errorMessage by mutableStateOf(0)

	fun showErrorMessage(messageResId: Int) = coroutineScope.launch {
		if (errorMessage == messageResId) return@launch

		errorMessage = messageResId
		isErrorVisible = true
		delay(1500)
		isErrorVisible = false
	}

	fun resetUiState() {
		isErrorVisible = false
		errorMessage = 0
	}
}