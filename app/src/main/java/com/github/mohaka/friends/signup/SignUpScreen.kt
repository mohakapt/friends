package com.github.mohaka.friends.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mohaka.friends.R

@Composable
@Preview(device = Devices.PIXEL_4)
fun SignUpScreen() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(16.dp)
	) {
		ScreenTitle(R.string.text_createAccount)

		Spacer(modifier = Modifier.height(16.dp))

		var email by remember { mutableStateOf("") }
		var password by remember { mutableStateOf("") }

		EmailField(
			value = email,
			onValueChange = { email = it }
		)

		PasswordField(
			value = password,
			onValueChange = { password = it }
		)

		Spacer(modifier = Modifier.height(16.dp))

		Button(
			modifier = Modifier.fillMaxWidth(),
			onClick = { },
			content = { Text(text = stringResource(id = R.string.action_signUp)) }
		)
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
private fun EmailField(value: String, onValueChange: (String) -> Unit) {
	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = value,
		label = { Text(text = stringResource(id = R.string.hint_email)) },
		onValueChange = onValueChange
	)
}

@Composable
private fun PasswordField(value: String, onValueChange: (String) -> Unit) {
	var isPasswordVisible by remember { mutableStateOf(false) }

	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(),
		value = value,
		trailingIcon = {
			IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
				Icon(
					painter = painterResource(id = if (isPasswordVisible) R.drawable.ic_password_hide else R.drawable.ic_password_show),
					contentDescription = stringResource(id = R.string.action_togglePasswordVisiblitiy)
				)
			}
		},
		visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
		label = { Text(text = stringResource(id = R.string.hint_password)) },
		onValueChange = onValueChange
	)
}
