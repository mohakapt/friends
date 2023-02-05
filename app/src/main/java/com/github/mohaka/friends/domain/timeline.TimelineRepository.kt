package com.github.mohaka.friends.domain

import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.ConnectionException
import com.github.mohaka.friends.domain.post.PostCatalog
import com.github.mohaka.friends.domain.user.UserCatalog
import com.github.mohaka.friends.timeline.state.TimelineState

class TimelineRepository(
	private val userCatalog: UserCatalog,
	private val postCatalog: PostCatalog,
) {
	suspend fun getTimelineFor(userUuid: String) = try {
		val userIds = listOf(userUuid) + userCatalog.followedBy(userUuid)
		val posts = postCatalog.postsFor(userIds)
		TimelineState.Posts(posts)
	} catch (e: BackendException) {
		TimelineState.BackendError
	} catch (e: ConnectionException) {
		TimelineState.OfflineError
	}
}