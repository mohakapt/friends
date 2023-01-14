package com.github.mohaka.friends.timeline

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.mohaka.friends.R

@Composable
fun TimelineScreen() {
	Text(text = stringResource(R.string.title_timeline))
}