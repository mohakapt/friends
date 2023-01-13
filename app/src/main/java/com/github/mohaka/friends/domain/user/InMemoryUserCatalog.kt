package com.github.mohaka.friends.domain.user

import com.github.mohaka.friends.domain.exceptions.DuplicateAccountException

class InMemoryUserCatalog(private val users: ArrayList<User> = arrayListOf()) {
	fun createUser(email: String, password: String, about: String): User {
		checkAccountDuplication(email)
		val userId = generateUuidFor(email)
		val user = User(userId, email, about)
		users.add(user)

		return user
	}

	private fun generateUuidFor(email: String) = ":" + email.takeWhile { it != '@' } + "Id:"

	private fun checkAccountDuplication(email: String) {
		if (users.any { it.email == email })
			throw DuplicateAccountException()
	}
}