package com.github.mohaka.friends.signup.state

import com.github.mohaka.friends.domain.user.User

sealed class SignUpState {
	data class SignedUp(val user: User) : SignUpState()

	object BadEmail : SignUpState()
	object BadPassword : SignUpState()
	object DuplicateAccount : SignUpState()
}
