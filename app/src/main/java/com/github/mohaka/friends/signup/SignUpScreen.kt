package com.github.mohaka.friends.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mohaka.friends.R
import com.github.mohaka.friends.signup.state.SignUpState

@Composable
@Preview(device = Devices.PIXEL_4)
fun SignUpScreen(
	viewModel: SignUpViewModel,
	onSignedUp: () -> Unit
) {
	var email by remember { mutableStateOf("") }
	var password by remember { mutableStateOf("") }
	var about by remember { mutableStateOf("") }
	var reportedSignedUp by remember { mutableStateOf(false) }
	val signUpState by viewModel.signUpState.observeAsState()

	if (signUpState is SignUpState.SignedUp && !reportedSignedUp) {
		reportedSignedUp = true
		onSignedUp()
	}

	Box(modifier = Modifier.fillMaxSize()) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp)
		) {
			ScreenTitle(R.string.text_createAccount)

			Spacer(modifier = Modifier.height(16.dp))

			EmailField(
				value = email,
				isError = signUpState is SignUpState.BadEmail,
				onValueChange = { email = it }
			)

			PasswordField(
				value = password,
				isError = signUpState is SignUpState.BadPassword,
				onValueChange = { password = it }
			)

			AboutField(
				value = about,
				onValueChange = { about = it }
			)

			Spacer(modifier = Modifier.height(16.dp))

			Button(
				modifier = Modifier.fillMaxWidth(),
				onClick = { viewModel.createAccount(email, password, "") },
				content = { Text(text = stringResource(R.string.action_signUp)) }
			)
		}

		when (signUpState) {
			is SignUpState.DuplicateAccount -> InfoMessage(R.string.error_duplicateAccount)
			is SignUpState.BackendError -> InfoMessage(R.string.error_backendError)
			is SignUpState.OfflineError -> InfoMessage(R.string.error_noConnection)
			else -> {}
		}
	}
}

@Composable
fun InfoMessage(@StringRes messageResId: Int) {
	Surface(
		modifier = Modifier.fillMaxWidth(),
		color = colors.error,
		elevation = 4.dp,
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Center,
		) {
			Text(
				modifier = Modifier.padding(16.dp),
				text = stringResource(messageResId),
				color = colors.onError,
			)
		}
	}
}

@Composable
private fun ScreenTitle(@StringRes id: Int) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center
	) {
		Text(
			text = stringResource(id),
			style = typography.h4
		)
	}
}

@Composable
private fun EmailField(
	value: String,
	isError: Boolean,
	onValueChange: (String) -> Unit,
) {
	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = value,
		isError = isError,
		label = { Text(text = stringResource(R.string.hint_email)) },
		onValueChange = onValueChange
	)
	if (isError) {
		Text(
			text = stringResource(R.string.error_invalidEmail),
			color = colors.error,
			style = typography.caption,
			modifier = Modifier.padding(start = 16.dp)
		)
	}
}

@Composable
private fun PasswordField(
	value: String,
	isError: Boolean,
	onValueChange: (String) -> Unit
) {
	var isPasswordVisible by remember { mutableStateOf(false) }
	val visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = value,
		isError = isError,
		trailingIcon = {
			VisibilityToggle(isPasswordVisible) {
				isPasswordVisible = !isPasswordVisible
			}
		},
		visualTransformation = visualTransformation,
		label = { Text(text = stringResource(R.string.hint_password)) },
		onValueChange = onValueChange
	)
	if (isError) {
		Text(
			text = stringResource(R.string.error_invalidPassword),
			color = colors.error,
			style = typography.caption,
			modifier = Modifier.padding(start = 16.dp)
		)
	}
}

@Composable
private fun VisibilityToggle(value: Boolean, onToggle: () -> Unit) {
	val painterId = if (value) R.drawable.ic_hide else R.drawable.ic_show

	IconButton(onClick = onToggle) {
		Icon(
			painter = painterResource(painterId),
			contentDescription = stringResource(R.string.action_toggleVisibility)
		)
	}
}

@Composable
private fun AboutField(value: String, onValueChange: (String) -> Unit) {
	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = value,
		label = { Text(text = stringResource(R.string.hint_about)) },
		onValueChange = onValueChange
	)
}
