package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mohaka.friends.domain.post.Post
import com.github.mohaka.friends.domain.user.Following
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

		val followings = listOf(
			Following("saraId", "lucyId"),
			Following("annaId", "lucyId"),
		)

		val userIds = listOf(userUuid) + followings
			.filter { it.userUuid == userUuid }
			.map { it.followingUuid }

		val posts = availablePosts.filter { userIds.contains(it.userUuid) }
		mutableSignUpState.value = TimelineState.Posts(posts)
	}
}
