package com.github.mohaka.friends.timeline

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.infrastructure.builder.UserBuilder
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
		val tim = UserBuilder.aUser().withUuid("timId").build()
		val timPosts = arrayListOf(Post("postId", tim.uuid, "Some content", 1L))

		viewModel.timelineFor(tim.uuid)

		assertEquals(TimelineState.Posts(timPosts), viewModel.timelineState.value)
	}
}