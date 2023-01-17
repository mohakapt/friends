package com.github.mohaka.friends

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
	val background: CoroutineDispatcher
}

class AppDispatchers : CoroutineDispatchers {
	override val background = Dispatchers.IO
}