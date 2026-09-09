package com.ogsedas.openmovies.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ogsedas.openmovies.navigation.MainTab
import com.ogsedas.openmovies.ui.theme.AppTheme

@Composable
fun BottomNavBar(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.colors

    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = colors.border, shape = RoundedCornerShape(0.dp))
            .background(colors.surface)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainTab.entries.forEach { tab ->
                val isSelected = tab == currentTab
                val itemColor = if (isSelected) colors.primary else colors.textMuted

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f) // Perfectly 50/50 proportioned
                        .clickable { onTabSelected(tab) }
                        .padding(vertical = 4.dp)
                ) {
                    // Icon above text
                    when (tab) {
                        MainTab.MOVIES -> {
                            BillboardTabIcon(tint = itemColor, isSelected = isSelected)
                        }
                        MainTab.PROFILE -> {
                            TicketTabIcon(tint = itemColor, isSelected = isSelected)
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = tab.title,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                        color = itemColor
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Active indicator bar
                    Box(
                        modifier = Modifier
                            .height(2.5.dp)
                            .size(width = 36.dp, height = 2.5.dp)
                            .background(
                                color = if (isSelected) colors.primary else Color.Transparent,
                                shape = RoundedCornerShape(1.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun BillboardTabIcon(tint: Color, isSelected: Boolean) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height

        // Film Reel / Clapperboard Cinema Screen Frame
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.12f, h * 0.18f),
            size = Size(w * 0.76f, h * 0.64f),
            cornerRadius = CornerRadius(6f, 6f),
            style = if (isSelected) Fill else Stroke(width = 3.5f)
        )

        // Inside cinema play symbol or screen detail
        if (isSelected) {
            val playPath = Path().apply {
                moveTo(w * 0.42f, h * 0.38f)
                lineTo(w * 0.64f, h * 0.50f)
                lineTo(w * 0.42f, h * 0.62f)
                close()
            }
            drawPath(
                path = playPath,
                color = Color.White
            )
        } else {
            // Horizontal film strip divider
            drawLine(
                color = tint,
                start = Offset(w * 0.12f, h * 0.42f),
                end = Offset(w * 0.88f, h * 0.42f),
                strokeWidth = 2.5f
            )
        }
    }
}

@Composable
private fun TicketTabIcon(tint: Color, isSelected: Boolean) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val w = size.width
        val h = size.height

        // Admission Ticket Pass shape with side notch cutouts
        val ticketPath = Path().apply {
            moveTo(w * 0.15f, h * 0.20f)
            lineTo(w * 0.85f, h * 0.20f)
            lineTo(w * 0.85f, h * 0.42f)
            // Right notch cutout
            quadraticTo(w * 0.72f, h * 0.50f, w * 0.85f, h * 0.58f)
            lineTo(w * 0.85f, h * 0.80f)
            lineTo(w * 0.15f, h * 0.80f)
            lineTo(w * 0.15f, h * 0.58f)
            // Left notch cutout
            quadraticTo(w * 0.28f, h * 0.50f, w * 0.15f, h * 0.42f)
            close()
        }

        drawPath(
            path = ticketPath,
            color = tint,
            style = if (isSelected) Fill else Stroke(width = 3.5f)
        )

        if (isSelected) {
            // Dashed inner perforation line
            drawLine(
                color = Color.White,
                start = Offset(w * 0.36f, h * 0.50f),
                end = Offset(w * 0.64f, h * 0.50f),
                strokeWidth = 2.5f
            )
        }
    }
}
