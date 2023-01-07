package com.yvkalume.lyri.ui.screen.lyrics.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LyricItem(
		modifier: Modifier = Modifier,
		text: String,
		isActive: Boolean
) {
		Text(
				text = text,
				fontSize = 20.sp,
				fontWeight = FontWeight.W700,
				color = if (isActive) {
						Color.White
				} else {
						Color.Black
				},
				modifier = Modifier.padding(vertical = 1.dp)
		)
}