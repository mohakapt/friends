package com.github.mohaka.friends.signup.state

sealed class SignUpState {
	object BadEmail: SignUpState()
}
