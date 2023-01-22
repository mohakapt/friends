package com.github.mohaka.friends.timeline

import com.github.mohaka.friends.InstantTaskExecuteExtension
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
}