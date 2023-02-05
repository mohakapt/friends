package com.github.mohaka.friends.timeline

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.post.InMemoryPostCatalog
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.domain.user.Following
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.infrastructure.builder.UserBuilder.Companion.aUser
import com.github.mohaka.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class LoadPostsTest {

	private val tim = aUser().withUuid("timId").build()
	private val lucy = aUser().withUuid("lucyId").build()
	private val sara = aUser().withUuid("saraId").build()
	private val anna = aUser().withUuid("annaId").build()

	private val timPosts = listOf(
		Post("postId", tim.uuid, "Some content", 1L)
	)
	private val lucyPosts = listOf(
		Post("post2", lucy.uuid, "Content of post 2", 2L),
		Post("post1", lucy.uuid, "Content of post 1", 1L),
	)
	private val saraPosts = listOf(
		Post("post4", sara.uuid, "Content of post 4", 4L),
		Post("post3", sara.uuid, "Content of post 3", 3L),
	)

	private val availablePosts = timPosts + lucyPosts + saraPosts
	private val followings = listOf(
		Following(anna.uuid, lucy.uuid),
		Following(sara.uuid, lucy.uuid)
	)

	@Test
	fun noPostsAvailable() {
		val viewModel = TimelineViewModel(
			InMemoryUserCatalog(),
			InMemoryPostCatalog(availablePosts)
		)

		viewModel.timelineFor("tomId")

		assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
	}

	@Test
	fun postsAvailable() {
		val viewModel = TimelineViewModel(
			InMemoryUserCatalog(),
			InMemoryPostCatalog(availablePosts)
		)

		viewModel.timelineFor(tim.uuid)

		assertEquals(TimelineState.Posts(timPosts), viewModel.timelineState.value)
	}

	@Test
	fun postsFromFriends() {
		val viewModel = TimelineViewModel(
			InMemoryUserCatalog(followings = followings),
			InMemoryPostCatalog(availablePosts)
		)

		viewModel.timelineFor(anna.uuid)

		assertEquals(TimelineState.Posts(lucyPosts), viewModel.timelineState.value)
	}

	@Test
	fun postsFromMeAndFriends() {
		val viewModel = TimelineViewModel(
			InMemoryUserCatalog(followings = followings),
			InMemoryPostCatalog(availablePosts)
		)

		viewModel.timelineFor(sara.uuid)

		assertEquals(TimelineState.Posts(lucyPosts + saraPosts), viewModel.timelineState.value)
	}
}