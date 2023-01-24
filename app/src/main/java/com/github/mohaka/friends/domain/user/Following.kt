package com.github.mohaka.friends.domain.user

data class Following(
	val userUuid: String,
	val followingUuid: String,
)