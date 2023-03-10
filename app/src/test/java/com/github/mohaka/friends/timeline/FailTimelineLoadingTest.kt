package com.github.mohaka.friends.timeline

import com.github.mohaka.friends.InstantTaskExecuteExtension
import com.github.mohaka.friends.domain.TimelineRepository
import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.ConnectionException
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.domain.post.PostCatalog
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.timeline.state.TimelineState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecuteExtension::class)
class FailTimelineLoadingTest {

	@Test
	fun backendError() {
		val userCatalog = InMemoryUserCatalog()
		val postCatalog = UnavailablePostCatalog()
		val viewModel = TimelineViewModel(TimelineRepository(userCatalog, postCatalog))

		viewModel.timelineFor(":userId:")

		assertEquals(TimelineState.BackendError, viewModel.timelineState.value)
	}

	@Test
	fun offlineError() {
		val userCatalog = InMemoryUserCatalog()
		val postCatalog = OfflinePostCatalog()
		val viewModel = TimelineViewModel(TimelineRepository(userCatalog, postCatalog))

		viewModel.timelineFor(":userId:")

		assertEquals(TimelineState.OfflineError, viewModel.timelineState.value)
	}

	class UnavailablePostCatalog : PostCatalog {
		override fun postsFor(userUuids: List<String>): List<Post> {
			throw BackendException()
		}
	}

	class OfflinePostCatalog : PostCatalog {
		override fun postsFor(userUuids: List<String>): List<Post> {
			throw ConnectionException()
		}
	}
}