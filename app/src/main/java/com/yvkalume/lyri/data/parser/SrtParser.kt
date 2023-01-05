package com.yvkalume.lyri.data.parser

import android.content.Context
import com.yvkalume.lyri.data.model.Lyric
import com.yvkalume.lyri.data.model.Timestamp
import com.yvkalume.lyri.util.ParsingException
import com.yvkalume.lyri.util.TimeStampParsingException
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class SrtParser @Inject constructor(@ApplicationContext private val context: Context) {
		fun parse(): List<Lyric> {
				val reader = BufferedReader(InputStreamReader(context.assets.open("youssoupha.srt")))

				val portionList = mutableListOf<Lyric>()

				reader.useLines { lines ->
						var index: String? = null
						var begin: Timestamp? = null
						var end: Timestamp? = null
						var text: String? = null


						lines.forEach {
								val line = it.trim()

								if (line.isNotEmpty()) {
										println(line)
										when {
												(index == null) -> index = line
												(begin == null) -> {
														val timestamps = line.split(" --> ")

														try {
																if (timestamps.size != 2) {
																		throw TimeStampParsingException("invalid timestamp")
																}
																begin = parseTimestamp(timestamps[0].trim())
																end = parseTimestamp(timestamps[1].trim())
														} catch (e: ParsingException) {
																throw TimeStampParsingException(e.message)
														}
												}
												(text == null) -> text = line
												else -> text += "\n$line"
										}
								} else if (text != null) {
										portionList.add(
												Lyric(
														index = index!!,
														startTimeStamp = begin!!,
														endTimeStamp = end!!,
														text = text!!
												)
										)
										index = null
										begin = null
										end = null
										text = null
								}

						}
				}

				return portionList
		}
}

private fun parseTimestamp(timestamp: String): Timestamp {
		try {
				val parts = timestamp.split(":", ",")
				return Timestamp(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
		} catch (e: Exception) {
				throw TimeStampParsingException("Invalid timestamp: '$timestamp'")
		}
}