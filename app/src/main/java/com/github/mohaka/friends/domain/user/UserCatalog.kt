package com.github.mohaka.friends.domain.user

interface UserCatalog {
	suspend fun createUser(email: String, password: String, about: String): User

	suspend fun followedBy(userUuid: String): List<String>
}