package com.yvkalume.lyri.data.model

data class Lyric(
		val index: String = "",
		val startTimeStamp: Timestamp,
		val endTimeStamp: Timestamp,
		val text: String = ""
)
