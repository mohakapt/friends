package com.github.mohaka.friends.domain.post

class InMemoryPostCatalog(
	private val availablePosts: List<Post>,
) : PostCatalog {
	override fun postsFor(userUuids: List<String>): List<Post> {
		return availablePosts.filter { userUuids.contains(it.userUuid) }
	}
}