package com.github.mohaka.friends.domain.post

class InMemoryPostCatalog {
	fun postsFor(userUuids: List<String>): List<Post> {
		val availablePosts = listOf(
			Post("postId", "timId", "Some content", 1L),
			Post("post2", "lucyId", "Content of post 2", 2L),
			Post("post1", "lucyId", "Content of post 1", 1L),
			Post("post4", "saraId", "Content of post 4", 4L),
			Post("post3", "saraId", "Content of post 3", 3L),
		)

		return availablePosts.filter { userUuids.contains(it.userUuid) }
	}
}