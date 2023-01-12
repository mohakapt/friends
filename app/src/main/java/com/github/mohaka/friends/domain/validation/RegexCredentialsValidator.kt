package com.github.mohaka.friends.domain.validation

import java.util.regex.Pattern

class RegexCredentialsValidator {
	fun validate(email: String, password: String): CredentialsValidationResult {
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