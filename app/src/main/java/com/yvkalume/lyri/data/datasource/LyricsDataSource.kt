package com.yvkalume.lyri.data.datasource

import com.yvkalume.lyri.data.model.Lyric
import com.yvkalume.lyri.data.parser.SrtParser
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LyricsDataSource @Inject constructor(private val srtParser: SrtParser) {
		suspend fun getLyrics() : List<Lyric> {
				return withContext(Dispatchers.IO) {
						srtParser.parse()
				}
		}

		fun getLyricsStream() = flow {
				emit(Unit)
		}
}