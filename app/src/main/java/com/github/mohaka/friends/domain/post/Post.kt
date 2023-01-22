package com.github.mohaka.friends.domain.post

data class Post(
	val uuid: String,
	val userUuid: String,
	val content: String,
	val timestamp: Long,
)
