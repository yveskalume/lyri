package com.yvkalume.lyri.data.model

data class Timestamp(
		private val hours: Int,
		private val minutes: Int,
		private val seconds: Int,
		private val milliseconds: Int
) {
		companion object {
				private const val HOUR_IN_MS = 60 * 60 * 1000L
				private const val MINUTE_IN_MS = 60 * 1000L
				private const val SECOND_IN_MS = 1000L
		}

		fun fromMilliseconds(milliseconds: Long): Timestamp {
				var remainder = milliseconds

				val hours = remainder / HOUR_IN_MS
				if (hours > 0L) {
						remainder %= HOUR_IN_MS
				}

				val minutes = remainder / MINUTE_IN_MS
				if (minutes > 0L) {
						remainder %= MINUTE_IN_MS
				}

				val seconds = remainder / SECOND_IN_MS
				if (seconds > 0L) {
						remainder %= SECOND_IN_MS
				}

				return Timestamp(hours.toInt(), minutes.toInt(), seconds.toInt(), remainder.toInt())
		}

		fun toMilliseconds(): Long = hours * HOUR_IN_MS + minutes * MINUTE_IN_MS + seconds * SECOND_IN_MS + milliseconds
}