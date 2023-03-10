package com.github.mohaka.friends.domain.user

import com.github.mohaka.friends.domain.exceptions.DuplicateAccountException

class InMemoryUserCatalog(
	private val users: MutableList<User> = mutableListOf(),
	private val followings: List<Following> = mutableListOf(),
) : UserCatalog {
	override suspend fun createUser(email: String, password: String, about: String): User {
		checkAccountDuplication(email)
		val userId = generateUuidFor(email)
		val user = User(userId, email, about)
		saveUser(user)

		return user
	}

	override suspend fun followedBy(userUuid: String): List<String> {
		return followings
			.filter { it.userUuid == userUuid }
			.map { it.followingUuid }
	}

	private fun checkAccountDuplication(email: String) {
		if (users.any { it.email == email })
			throw DuplicateAccountException()
	}

	private fun generateUuidFor(email: String) = ":" + email.takeWhile { it != '@' } + "Id:"

	private fun saveUser(user: User) = users.add(user)
}