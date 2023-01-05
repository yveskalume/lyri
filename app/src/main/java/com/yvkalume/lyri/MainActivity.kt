package com.yvkalume.lyri

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yvkalume.lyri.data.datasource.LyricsDataSource
import com.yvkalume.lyri.data.model.Lyric
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
								val contentFlow: MutableStateFlow<List<Lyric>> = MutableStateFlow(listOf())
								val content by contentFlow.collectAsState()

								LaunchedEffect(Unit) {
										contentFlow.emit(lyricsDataSource.getLyrics())
								}

								Surface(
										modifier = Modifier.fillMaxSize(),
										color = MaterialTheme.colors.background
								) {
										LazyColumn(
												modifier = Modifier.fillMaxSize(),
												verticalArrangement = Arrangement.spacedBy(8.dp),
												contentPadding = PaddingValues(16.dp)
										) {
												items(content) {
														Text(text = it.text, fontSize = 18.sp)
												}
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