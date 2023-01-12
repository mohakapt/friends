package com.github.mohaka.friends.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.Center
		) {
			Text(
				text = stringResource(R.string.text_createAccount),
				style = typography.h4
			)
		}

		Spacer(modifier = Modifier.height(16.dp))

		OutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			value = "",
			label = { Text(text = stringResource(id = R.string.hint_email)) },
			onValueChange = {}
		)

		OutlinedTextField(
			modifier = Modifier.fillMaxWidth(),
			value = "",
			label = { Text(text = stringResource(id = R.string.hint_password)) },
			onValueChange = {}
		)

		Spacer(modifier = Modifier.height(16.dp))

		Button(
			modifier = Modifier.fillMaxWidth(),
			onClick = { },
			content = { Text(text = stringResource(id = R.string.action_signUp)) }
		)
	}
}