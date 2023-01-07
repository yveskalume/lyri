package com.yvkalume.lyri.ui.screen.lyrics.component

import android.util.Log
import android.widget.SeekBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.yvkalume.lyri.ui.customview.CustomSeekBar
import com.yvkalume.lyri.ui.customview.rememberSeekBarChangeListener

@Composable
fun PlayerController(
		modifier: Modifier = Modifier,
		isPlaying: Boolean,
		playerElapsedText: String,
		playerDurationText: String,
		progress: Int,
		max: Int,
		onPauseClick: () -> Unit,
		onUserProgressChange: (Int) -> Unit,
		startTrackingTouch: (seekBar: SeekBar?) -> Unit,
		stopTrackingTouch: (seekBar: SeekBar) -> Unit
) {

		val seekBarListener = rememberSeekBarChangeListener(
				onUserProgressChange = onUserProgressChange,
				startTrackingTouch = startTrackingTouch,
				stopTrackingTouch = stopTrackingTouch
		)

		Column(
				modifier = Modifier
						.wrapContentHeight()
						.fillMaxWidth()
						.then(modifier)
		) {
				CustomSeekBar(
						progress = progress,
						max = max,
						changeListener = seekBarListener,
						modifier = Modifier.fillMaxWidth()
				)
		}
}