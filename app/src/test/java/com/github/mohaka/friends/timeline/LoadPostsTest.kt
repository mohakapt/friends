package com.github.mohaka.friends.timeline

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.domain.user.User
import com.github.mohaka.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class LoadPostsTest {
	@Test
	fun noPostsAvailable() {
		val viewModel = TimelineViewModel()

		viewModel.timelineFor("annaId")

		assertEquals(TimelineState.Posts(emptyList()), viewModel.timelineState.value)
	}

	@Test
	fun postsAvailable() {
		val viewModel = TimelineViewModel()
		val jason = User(":jasonId:", "jason@friends.com", ":about jason:")
		val jasonPosts = arrayListOf(Post(":postId:", jason.uuid, ":Some content:", 1L))

		viewModel.timelineFor(jason.uuid)

		assertEquals(TimelineState.Posts(jasonPosts), viewModel.timelineState.value)
	}
}