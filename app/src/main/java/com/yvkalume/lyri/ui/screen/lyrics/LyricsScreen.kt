package com.yvkalume.lyri.ui.screen.lyrics

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.yvkalume.lyri.R
import com.yvkalume.lyri.data.model.Song
import com.yvkalume.lyri.ui.screen.lyrics.component.PlayerController
import kotlinx.coroutines.delay

@Composable
fun LyricsScreen(song: Song, modifier: Modifier = Modifier) {

		val isPlaying by remember { mutableStateOf(true) }
		val shouldUpdateProgress = remember { mutableStateOf(true) }
		val playerPosition = remember { mutableStateOf(0L) }
		val playerElapsedText = remember { mutableStateOf("00:00") }
		val playerDurationText = remember { mutableStateOf("00:00") }
		val scrollableLyricsOffset = remember { mutableStateOf(0) }
		val currentPlayingLyricsIndex = remember { mutableStateOf(0) }
		val lazyListState = rememberLazyListState()
		val isLyricsScrolling = lazyListState.interactionSource.collectIsDraggedAsState()
		val isLyricsPressed = lazyListState.interactionSource.collectIsPressedAsState()
		var seekbarProgress by remember {
				mutableStateOf(0)
		}

		val animatedFloat = animateFloatAsState(
				targetValue = if ((lazyListState.firstVisibleItemIndex == 0 &&
										lazyListState.firstVisibleItemScrollOffset == 0)
				) 0f else 1f,
				animationSpec = tween(durationMillis = 300, easing = LinearEasing)
		)

		val songUri = RawResourceDataSource.buildRawResourceUri(R.raw.youssoupha)
		val mediaItem = MediaItem.Builder()
				.setUri(songUri)
				.setMediaId(song.title.replace(" ", ""))
				.setTag(songUri)
				.setMediaMetadata(
						MediaMetadata.Builder()
								.setDisplayTitle(song.title)
								.build()
				).build()

		val context = LocalContext.current
		val exoPlayer = remember {
				ExoPlayer.Builder(context).build().apply {
						setPlaybackSpeed(1f)
						setMediaItem(mediaItem)
						prepare()
				}
		}

		LaunchedEffect(Unit) {
				exoPlayer.play()
		}

		LaunchedEffect(true) {
				try {
						while (true) {
								playerPosition.value = exoPlayer.currentPosition
								if (shouldUpdateProgress.value) {
										seekbarProgress = (playerPosition.value / 1_000).toInt()
										playerElapsedText.value = longMillisToTime(exoPlayer.currentPosition)
										playerDurationText.value = longMillisToTime(exoPlayer.duration)
								}
								if (isPlaying) {
										val nextIndex = song.lyrics.indexOfFirst {
												it.startTimeStamp.toMilliseconds() > playerPosition.value
										}


										if (nextIndex >= 0 && nextIndex != currentPlayingLyricsIndex.value) {
												currentPlayingLyricsIndex.value = nextIndex
												if (!isLyricsPressed.value && !isLyricsScrolling.value) {
														lazyListState.animateScrollToItem(
																currentPlayingLyricsIndex.value,
																scrollOffset = -scrollableLyricsOffset.value
														)
												}
										}
								}
								delay(200)
						}
				} catch (_: Exception) {

				}
		}

		Scaffold(
				backgroundColor = Color.Blue.copy(alpha = 0.3f),
				modifier = modifier
						.fillMaxSize()
						.onGloballyPositioned {
								scrollableLyricsOffset.value = it.size.height / 2
						},
				bottomBar = {
						PlayerController(
								isPlaying = isPlaying,
								playerElapsedText = playerElapsedText.value,
								playerDurationText = playerDurationText.value,
								progress = seekbarProgress,
								max = (exoPlayer.duration / 1_000).toInt(),
								onPauseClick = {},
								onUserProgressChange = { progress ->
										playerElapsedText.value = longMillisToTime(progress.toLong() * 1_000)
								},
								startTrackingTouch = {
										shouldUpdateProgress.value = false
								},
								stopTrackingTouch = { seekbar ->
										shouldUpdateProgress.value = true
										exoPlayer.seekTo(seekbar.progress.toLong() * 1_000)
								}
						)
				}
		) { contentPadding ->
				LyricsContent(
						song = song,
						lazyListState = lazyListState,
						animatedFloat = animatedFloat.value,
						playerPosition = playerPosition.value,
						modifier = Modifier
								.padding(contentPadding)
								.fillMaxSize()
				)
		}
}

private fun floatToTime(time: Float): String {
		val minutes = (time / 60).toInt()
		val seconds = (time % 60).toInt()
		return String.format("%02d:%02d", minutes, seconds)
}

private fun longMillisToTime(time: Long): String {
		val minutes = (time / 1_000 / 60).toInt()
		val seconds = (time / 1_000 % 60).toInt()
		return String.format("%02d:%02d", minutes, seconds)
}