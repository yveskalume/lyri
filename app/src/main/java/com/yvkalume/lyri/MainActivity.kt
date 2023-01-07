package com.yvkalume.lyri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yvkalume.lyri.data.datasource.LyricsDataSource
import com.yvkalume.lyri.data.model.Song
import com.yvkalume.lyri.ui.screen.lyrics.LyricsScreen
import com.yvkalume.lyri.ui.theme.LyriTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

		@Inject
		lateinit var lyricsDataSource: LyricsDataSource

		override fun onCreate(savedInstanceState: Bundle?) {
				super.onCreate(savedInstanceState)
				setContent {
						LyriTheme {
								// A surface container using the 'background' color from the theme
								val songFlow: MutableStateFlow<Song?> = MutableStateFlow(null)
								val song by songFlow.collectAsState()

								LaunchedEffect(Unit) {
										val lyrics = lyricsDataSource.getLyrics()
										songFlow.emit(Song(title = "Par amour", lyrics = lyrics))
								}

								Surface(
										modifier = Modifier.fillMaxSize(),
										color = MaterialTheme.colors.background
								) {

										song?.let {
												LyricsScreen(song = it, modifier = Modifier.fillMaxSize())
										}
								}
						}
				}
		}
}

@Composable
fun Greeting(name: String) {
		Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
		LyriTheme {
				Greeting("Android")
		}
}