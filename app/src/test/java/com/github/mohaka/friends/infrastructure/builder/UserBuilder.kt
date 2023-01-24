package com.github.mohaka.friends.infrastructure.builder

import com.github.mohaka.friends.domain.user.User
import java.util.UUID

class UserBuilder {
	private var userUuid = UUID.randomUUID().toString()
	private var userEmail = "user@friends.com"
	private var userAbout = "About user"

	fun withUuid(uuid: String) = this.apply { userUuid = uuid }

	fun withEmail(email: String) = this.apply { userEmail = email }

	fun withAbout(about: String) = this.apply { userAbout = about }

	fun build() = User(userUuid, userEmail, userAbout)

	companion object {
		fun aUser() = UserBuilder()
	}
}