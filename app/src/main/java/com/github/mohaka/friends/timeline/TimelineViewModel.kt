package com.github.mohaka.friends.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mohaka.friends.domain.TimelineRepository
import com.github.mohaka.friends.domain.post.PostCatalog
import com.github.mohaka.friends.domain.user.UserCatalog
import com.github.mohaka.friends.timeline.state.TimelineState
import kotlinx.coroutines.launch

class TimelineViewModel(
	private val timelineRepository: TimelineRepository,
) : ViewModel() {

	private val mutableTimelineState = MutableLiveData<TimelineState>()
	val timelineState: LiveData<TimelineState> = mutableTimelineState

	fun timelineFor(userUuid: String) = viewModelScope.launch {
		mutableTimelineState.value = timelineRepository.getTimelineFor(userUuid)
	}
}
