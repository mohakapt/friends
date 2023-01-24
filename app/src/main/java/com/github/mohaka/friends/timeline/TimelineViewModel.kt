package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.timeline.state.TimelineState

class TimelineViewModel : ViewModel() {
	private val mutableSignUpState = MutableLiveData<TimelineState>()
	val timelineState: LiveData<TimelineState> = mutableSignUpState

	fun timelineFor(userUuid: String) {
		if (userUuid.contains("tim")) {
			val post = Post("postId", userUuid, "Some content", 1L)
			val posts = listOf(post)
			mutableSignUpState.value = TimelineState.Posts(posts)
		} else {
			mutableSignUpState.value = TimelineState.Posts(emptyList())
		}
	}
}
