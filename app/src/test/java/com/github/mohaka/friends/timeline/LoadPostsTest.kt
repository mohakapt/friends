package com.github.mohaka.friends.timeline

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.post.InMemoryPostCatalog
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.github.mohaka.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class LoadPostsTest {
	@Test
	fun noPostsAvailable() {
		val viewModel = TimelineViewModel(InMemoryUserCatalog(), InMemoryPostCatalog(listOf(
			Post("postId", "timId", "Some content", 1L),
			Post("post2", "lucyId", "Content of post 2", 2L),
			Post("post1", "lucyId", "Content of post 1", 1L),
			Post("post4", "saraId", "Content of post 4", 4L),
			Post("post3", "saraId", "Content of post 3", 3L),
		)))

		viewModel.timelineFor("topId")

		assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
	}

	@Test
	fun postsAvailable() {
		val viewModel = TimelineViewModel(InMemoryUserCatalog(), InMemoryPostCatalog(listOf(
			Post("postId", "timId", "Some content", 1L),
			Post("post2", "lucyId", "Content of post 2", 2L),
			Post("post1", "lucyId", "Content of post 1", 1L),
			Post("post4", "saraId", "Content of post 4", 4L),
			Post("post3", "saraId", "Content of post 3", 3L),
		)))
		val tim = aUser().withUuid("timId").build()
		val timPosts = arrayListOf(Post("postId", tim.uuid, "Some content", 1L))

		viewModel.timelineFor(tim.uuid)

		assertEquals(TimelineState.Posts(timPosts), viewModel.timelineState.value)
	}

	@Test
	fun postsFromFriends() {
		val anna = aUser().withUuid("annaId").build()
		val lucy = aUser().withUuid("lucyId").build()
		val lucyPosts = listOf(
			Post("post2", lucy.uuid, "Content of post 2", 2L),
			Post("post1", lucy.uuid, "Content of post 1", 1L),
		)

		val viewModel = TimelineViewModel(InMemoryUserCatalog(), InMemoryPostCatalog(listOf(
			Post("postId", "timId", "Some content", 1L),
			Post("post2", "lucyId", "Content of post 2", 2L),
			Post("post1", "lucyId", "Content of post 1", 1L),
			Post("post4", "saraId", "Content of post 4", 4L),
			Post("post3", "saraId", "Content of post 3", 3L),
		)))
		viewModel.timelineFor(anna.uuid)

		assertEquals(TimelineState.Posts(lucyPosts), viewModel.timelineState.value)
	}

	@Test
	fun postsFromMeAndFriends() {
		val lucy = aUser().withUuid("lucyId").build()
		val lucyPosts = listOf(
			Post("post2", lucy.uuid, "Content of post 2", 2L),
			Post("post1", lucy.uuid, "Content of post 1", 1L),
		)

		val sara = aUser().withUuid("saraId").build()
		val saraPosts = listOf(
			Post("post4", sara.uuid, "Content of post 4", 4L),
			Post("post3", sara.uuid, "Content of post 3", 3L),
		)

		val viewModel = TimelineViewModel(InMemoryUserCatalog(), InMemoryPostCatalog(listOf(
			Post("postId", "timId", "Some content", 1L),
			Post("post2", "lucyId", "Content of post 2", 2L),
			Post("post1", "lucyId", "Content of post 1", 1L),
			Post("post4", "saraId", "Content of post 4", 4L),
			Post("post3", "saraId", "Content of post 3", 3L),
		)))
		viewModel.timelineFor(sara.uuid)
		assertEquals(TimelineState.Posts(lucyPosts + saraPosts), viewModel.timelineState.value)
	}
}