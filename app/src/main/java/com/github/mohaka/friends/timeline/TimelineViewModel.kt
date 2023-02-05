package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mohaka.friends.domain.post.InMemoryPostCatalog
import com.github.mohaka.friends.domain.user.InMemoryUserCatalog
import com.github.mohaka.friends.timeline.state.TimelineState

class TimelineViewModel : ViewModel() {
	private val mutableSignUpState = MutableLiveData<TimelineState>()
	val timelineState: LiveData<TimelineState> = mutableSignUpState

	fun timelineFor(userUuid: String) {
		val userIds = listOf(userUuid) + InMemoryUserCatalog().followedBy(userUuid)

		val posts = InMemoryPostCatalog().postsFor(userIds)
		mutableSignUpState.value = TimelineState.Posts(posts)
	}
}
