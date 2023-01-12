package com.github.mohaka.friends.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.github.mohaka.friends.R

@Composable
@Preview(device = Devices.PIXEL_4)
fun SignUpScreen() {
	Column(modifier = Modifier.fillMaxSize()) {
		Text(text = "Create an account")

		OutlinedTextField(
			value = "",
			label = { Text(text = stringResource(id = R.string.hint_email)) },
			onValueChange = {}
		)

		OutlinedTextField(
			value = "",
			label = { Text(text = stringResource(id = R.string.hint_password)) },
			onValueChange = {}
		)

		OutlinedButton(
			onClick = { },
			content = { Text(text = stringResource(id = R.string.action_signUp)) }
		)
	}
}