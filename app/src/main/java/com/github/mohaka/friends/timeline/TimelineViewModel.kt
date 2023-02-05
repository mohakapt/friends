package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohaka.friends.domain.exceptions.BackendException
import com.github.mohaka.friends.domain.exceptions.ConnectionException
import com.github.mohaka.friends.domain.post.PostCatalog
import com.github.mohaka.friends.domain.user.UserCatalog
import com.github.mohaka.friends.timeline.state.TimelineState
import kotlinx.coroutines.launch

class TimelineViewModel(
	private val userCatalog: UserCatalog,
	private val postCatalog: PostCatalog,
) : ViewModel() {

	private val mutableTimelineState = MutableLiveData<TimelineState>()
	val timelineState: LiveData<TimelineState> = mutableTimelineState

	fun timelineFor(userUuid: String) = viewModelScope.launch {
		try {
			val userIds = listOf(userUuid) + userCatalog.followedBy(userUuid)
			val posts = postCatalog.postsFor(userIds)
			mutableTimelineState.value = TimelineState.Posts(posts)
		} catch (e: BackendException) {
			mutableTimelineState.value = TimelineState.BackendError
		} catch (e: ConnectionException) {
			mutableTimelineState.value = TimelineState.OfflineError
		}
	}
}
