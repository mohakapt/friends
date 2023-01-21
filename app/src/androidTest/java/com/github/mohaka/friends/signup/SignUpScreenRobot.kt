package com.github.mohaka.friends.signup

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.mohaka.friends.MainActivity
import com.github.mohaka.friends.R

typealias ComposeRule<T> = AndroidComposeTestRule<ActivityScenarioRule<T>, T>

fun launchSignUpScreen(rule: ComposeRule<MainActivity>, block: SignUpRobot.() -> Unit) = SignUpRobot(rule).apply(block)

class SignUpRobot(private val rule: ComposeRule<MainActivity>) {
	fun typeEmail(email: String) {
		val emailHint = rule.activity.getString(R.string.hint_email)
		rule.onNodeWithText(emailHint)
			.performTextInput(email)
	}

	fun typePassword(password: String) {
		val passwordHint = rule.activity.getString(R.string.hint_password)
		rule.onNodeWithText(passwordHint)
			.performTextInput(password)
	}

	fun submit() {
		val signUp = rule.activity.getString(R.string.action_signUp)
		rule.onNodeWithText(signUp)
			.performClick()
	}

	infix fun verify(block: SignUpVerification.() -> Unit) = SignUpVerification(rule).apply(block)
}

class SignUpVerification(private val rule: ComposeRule<MainActivity>) {
	fun timelineScreenIsPresent() {
		val timeline = rule.activity.getString(R.string.title_timeline)
		rule.onNodeWithText(timeline)
			.assertIsDisplayed()
	}

	fun duplicateAccountErrorIsPresent() {
		val duplicateAccountError = rule.activity.getString(R.string.error_duplicateAccount)
		rule.onNodeWithText(duplicateAccountError)
			.assertIsDisplayed()
	}

	fun backendErrorIsPresent() {
		val backendError = rule.activity.getString(R.string.error_backendError)
		rule.onNodeWithText(backendError)
			.assertIsDisplayed()
	}

	fun offlineErrorIsPresent() {
		val offlineError = rule.activity.getString(R.string.error_noConnection)
		rule.onNodeWithText(offlineError)
			.assertIsDisplayed()
	}

	fun badEmailErrorIsPresent() {
		val badEmail = rule.activity.getString(R.string.error_invalidEmail)
		rule.onNodeWithText(badEmail)
			.assertIsDisplayed()
	}

	fun badPasswordErrorIsPresent() {
		val badPassword = rule.activity.getString(R.string.error_invalidPassword)
		rule.onNodeWithText(badPassword)
			.assertIsDisplayed()
	}

	fun badEmailErrorIsNotPresent() {
		val badEmail = rule.activity.getString(R.string.error_invalidEmail)
		rule.onNodeWithText(badEmail)
			.assertDoesNotExist()
	}

	fun badPasswordErrorIsNotPresent() {
		val badPassword = rule.activity.getString(R.string.error_invalidPassword)
		rule.onNodeWithText(badPassword)
			.assertDoesNotExist()
	}

	fun blockingLoadingIsPresent() {
		val loading = rule.activity.getString(R.string.text_loading)
		rule.onNodeWithTag(loading)
			.assertIsDisplayed()
	}
}
