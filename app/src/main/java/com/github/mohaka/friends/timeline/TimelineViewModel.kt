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
		val availablePosts = listOf(
			Post("postId", "timId", "Some content", 1L),
			Post("post2", "lucyId", "Content of post 2", 2L),
			Post("post1", "lucyId", "Content of post 1", 1L),
			Post("post4", "saraId", "Content of post 4", 4L),
			Post("post3", "saraId", "Content of post 3", 3L),
		)

		if (userUuid == "saraId") {
			val posts = availablePosts.filter { it.userUuid == "lucyId" || it.userUuid == "saraId" }
			mutableSignUpState.value = TimelineState.Posts(posts)
		} else if (userUuid == "annaId") {
			val posts = availablePosts.filter { it.userUuid == "lucyId" }
			mutableSignUpState.value = TimelineState.Posts(posts)
		} else if (userUuid == "timId") {
			val posts = availablePosts.filter { it.userUuid == userUuid }
			mutableSignUpState.value = TimelineState.Posts(posts)
		} else {
			mutableSignUpState.value = TimelineState.Posts(emptyList())
		}
	}
}
