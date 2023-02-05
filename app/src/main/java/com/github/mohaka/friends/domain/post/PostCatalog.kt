package com.github.mohaka.friends.domain.post

interface PostCatalog {

	fun postsFor(userUuids: List<String>): List<Post>
}