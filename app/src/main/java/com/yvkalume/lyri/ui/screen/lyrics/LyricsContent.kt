package com.yvkalume.lyri.ui.screen.lyrics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yvkalume.lyri.data.model.Song
import com.yvkalume.lyri.ui.screen.lyrics.component.LyricItem

@Composable
internal fun LyricsContent(
		modifier: Modifier = Modifier,
		lazyListState: LazyListState = rememberLazyListState(),
		song: Song,
		animatedFloat: Float,
		playerPosition: Long
) {
		LazyColumn(
				modifier = Modifier.fillMaxSize(),
				state = lazyListState,
				contentPadding = PaddingValues(vertical = 2.dp, horizontal = 20.dp),
				horizontalAlignment = Alignment.Start,
		) {
				items(
						song.lyrics,
						key = { it.startTimeStamp }
				) { item ->
						LyricItem(
								text = item.text,
								isActive = playerPosition in item.startTimeStamp.toMilliseconds()..
												item.endTimeStamp.toMilliseconds()
						)
				}
		}
}