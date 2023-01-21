package com.github.mohaka.friends.signup

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.github.mohaka.friends.R
import com.github.mohaka.friends.signup.state.SignUpScreenState
import com.github.mohaka.friends.signup.state.SignUpState

@Composable
fun SignUpScreen(
	viewModel: SignUpViewModel,
	onSignedUp: () -> Unit,
) {
	val coroutineScope = rememberCoroutineScope()
	val screenState by remember { mutableStateOf(SignUpScreenState(coroutineScope)) }
	val signUpState by viewModel.signUpState.observeAsState()

	when (signUpState) {
		is SignUpState.SignedUp -> {
			if (!screenState.reportedSignedUp) {
				screenState.reportedSignedUp = true
				onSignedUp()
			}
		}

		is SignUpState.Loading -> BlockingLoading()
		is SignUpState.BadEmail -> screenState.isEmailErrorVisible = true
		is SignUpState.BadPassword -> screenState.isPasswordErrorVisible = true
		is SignUpState.DuplicateAccount -> screenState.showErrorMessage(R.string.error_duplicateAccount)
		is SignUpState.BackendError -> screenState.showErrorMessage(R.string.error_backendError)
		is SignUpState.OfflineError -> screenState.showErrorMessage(R.string.error_noConnection)
		else -> {}
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
				value = screenState.email,
				isError = screenState.showBadEmail,
				onValueChange = { screenState.email = it }
			)

			PasswordField(
				value = screenState.password,
				isError = screenState.showBadPassword,
				onValueChange = { screenState.password = it }
			)

			AboutField(
				value = screenState.about,
				onValueChange = { screenState.about = it }
			)

			Spacer(modifier = Modifier.height(16.dp))

			Button(modifier = Modifier.fillMaxWidth(), onClick = {
				screenState.resetUiState()
				with(screenState) {
					viewModel.createAccount(email, password, about)
				}
			}, content = { Text(text = stringResource(R.string.action_signUp)) })
		}

		InfoMessage(
			isVisible = screenState.isErrorVisible, messageResId = screenState.errorMessage
		)
	}
}

@Composable
fun BlockingLoading() {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.testTag(stringResource(R.string.text_loading))
			.background(colors.surface.copy(alpha = 0.7f)),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator()
	}
}

@Composable
fun InfoMessage(
	isVisible: Boolean,
	@StringRes messageResId: Int,
) {
	AnimatedVisibility(
		visible = isVisible,
		enter = slideInVertically(
			initialOffsetY = { fullHeight -> -fullHeight },
			animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
		), exit = slideOutVertically(
			animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
			targetOffsetY = { fullHeight -> -fullHeight }
		)
	) {
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
}

@Composable
private fun ScreenTitle(@StringRes id: Int) {
	Row(
		modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
	) {
		Text(
			text = stringResource(id), style = typography.h4
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
			text = stringResource(R.string.error_invalidEmail), color = colors.error, style = typography.caption, modifier = Modifier.padding(start = 16.dp)
		)
	}
}

@Composable
private fun PasswordField(
	value: String, isError: Boolean, onValueChange: (String) -> Unit
) {
	var isPasswordVisible by remember { mutableStateOf(false) }
	val visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

	OutlinedTextField(modifier = Modifier.fillMaxWidth(), value = value, isError = isError, trailingIcon = {
		VisibilityToggle(isPasswordVisible) {
			isPasswordVisible = !isPasswordVisible
		}
	}, visualTransformation = visualTransformation, label = { Text(text = stringResource(R.string.hint_password)) }, onValueChange = onValueChange
	)
	if (isError) {
		Text(
			text = stringResource(R.string.error_invalidPassword), color = colors.error, style = typography.caption, modifier = Modifier.padding(start = 16.dp)
		)
	}
}

@Composable
private fun VisibilityToggle(value: Boolean, onToggle: () -> Unit) {
	val painterId = if (value) R.drawable.ic_hide else R.drawable.ic_show

	IconButton(onClick = onToggle) {
		Icon(
			painter = painterResource(painterId), contentDescription = stringResource(R.string.action_toggleVisibility)
		)
	}
}

@Composable
private fun AboutField(value: String, onValueChange: (String) -> Unit) {
	OutlinedTextField(
		modifier = Modifier.fillMaxWidth(), value = value, label = { Text(text = stringResource(R.string.hint_about)) }, onValueChange = onValueChange
	)
}
