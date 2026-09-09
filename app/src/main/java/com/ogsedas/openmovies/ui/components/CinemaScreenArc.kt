package com.ogsedas.openmovies.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ogsedas.openmovies.ui.theme.AppTheme

@Composable
fun CinemaScreenArc(
    modifier: Modifier = Modifier,
    hallLabel: String = "CINEMA SCREEN // HALL 04"
) {
    val colors = AppTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            val w = size.width
            val lineY = 18f

            // Clean architectural screen line
            drawLine(
                color = colors.primary,
                start = Offset(w * 0.12f, lineY),
                end = Offset(w * 0.88f, lineY),
                strokeWidth = 5f,
                cap = StrokeCap.Round
            )
        }

        Text(
            text = hallLabel,
            color = colors.textSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
