package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mohaka.friends.domain.post.InMemoryPostCatalog
import com.github.mohaka.friends.domain.user.Following
import com.github.mohaka.friends.timeline.state.TimelineState

class TimelineViewModel : ViewModel() {
	private val mutableSignUpState = MutableLiveData<TimelineState>()
	val timelineState: LiveData<TimelineState> = mutableSignUpState

	fun timelineFor(userUuid: String) {
		val followings = listOf(
			Following("saraId", "lucyId"),
			Following("annaId", "lucyId"),
		)

		val userIds = listOf(userUuid) + followings
			.filter { it.userUuid == userUuid }
			.map { it.followingUuid }

		val posts = InMemoryPostCatalog().postsFor(userIds)
		mutableSignUpState.value = TimelineState.Posts(posts)
	}
}
