package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohaka.friends.domain.post.InMemoryPostCatalog
import com.github.mohaka.friends.domain.user.UserCatalog
import com.github.mohaka.friends.timeline.state.TimelineState
import kotlinx.coroutines.launch

class TimelineViewModel(
	private val userCatalog: UserCatalog,
	private val postCatalog: InMemoryPostCatalog,
) : ViewModel() {

	private val mutableSignUpState = MutableLiveData<TimelineState>()
	val timelineState: LiveData<TimelineState> = mutableSignUpState

	fun timelineFor(userUuid: String) = viewModelScope.launch {
		val userIds = listOf(userUuid) + userCatalog.followedBy(userUuid)

		val posts = postCatalog.postsFor(userIds)
		mutableSignUpState.value = TimelineState.Posts(posts)
	}
}
