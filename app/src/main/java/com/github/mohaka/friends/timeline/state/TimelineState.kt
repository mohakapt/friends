package com.github.mohaka.friends.timeline.state

import com.github.mohaka.friends.domain.post.Post

sealed class TimelineState {
	data class Posts(val posts: List<Post>) : TimelineState()
	object BackendError : TimelineState()
	object OfflineError : TimelineState()
}
