package com.github.mohaka.friends

import kotlinx.coroutines.Dispatchers

class TestDispatchers : CoroutineDispatchers {
	override val background = Dispatchers.Unconfined
}