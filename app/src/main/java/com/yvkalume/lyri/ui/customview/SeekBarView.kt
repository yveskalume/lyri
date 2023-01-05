package com.yvkalume.lyri.ui.customview

import android.view.LayoutInflater
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yvkalume.lyri.R


@Composable
fun CustomSeekBar(
		modifier: Modifier = Modifier,
		progress: Int,
		max: Int,
		changeListener: OnSeekBarChangeListener
) {

		val context = LocalContext.current

		AndroidView(
				factory = { _ ->
						(LayoutInflater.from(context).inflate(
								R.layout.playback_seekbar,
								null
						) as SeekBar).apply {
								setOnSeekBarChangeListener(changeListener)
								this.setPadding(20, 0, 0, 20)
								this.max = max
								this.progress = progress
						}
				},
				modifier = modifier
		)
}

fun rememberSeekBarChangeListener(
		onUserProgressChange: (Int) -> Unit,
		startTrackingTouch: (seekBar: SeekBar?) -> Unit,
		stopTrackingTouch: (seekBar: SeekBar) -> Unit
): SeekBar.OnSeekBarChangeListener {
		return object : SeekBar.OnSeekBarChangeListener {
				override fun onProgressChanged(
						seekBar: SeekBar?,
						progress: Int,
						fromUser: Boolean
				) {
						if (fromUser) {
								onUserProgressChange(progress)
						}
				}

				override fun onStartTrackingTouch(seekBar: SeekBar?) {
						startTrackingTouch(seekBar)
				}

				override fun onStopTrackingTouch(seekBar: SeekBar?) {
						if (seekBar != null) {
								stopTrackingTouch(seekBar)
						}
				}
		}
}

