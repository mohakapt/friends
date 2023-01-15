package com.github.mohaka.friends.domain.user

import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.DuplicateAccountException
import com.github.mohaka.friends.domain.exceptions.NetworkException
import com.github.mohaka.friends.signup.state.SignUpState

class UserRepository(private val userCatalog: UserCatalog) {
	public fun signUp(email: String, password: String, about: String) = try {
		val user = userCatalog.createUser(email, password, about)
		SignUpState.SignedUp(user)
	} catch (e: DuplicateAccountException) {
		SignUpState.DuplicateAccount
	} catch (e: BackendException) {
		SignUpState.BackendError
	} catch (e: NetworkException) {
		SignUpState.OfflineError
	}
}