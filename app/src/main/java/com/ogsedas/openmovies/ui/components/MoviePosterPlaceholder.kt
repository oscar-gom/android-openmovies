package com.ogsedas.openmovies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ogsedas.openmovies.model.Movie
import com.ogsedas.openmovies.ui.theme.AppTheme

@Composable
fun MoviePosterPlaceholder(
    movie: Movie,
    modifier: Modifier = Modifier,
    isWide: Boolean = false
) {
    val colors = AppTheme.colors

    val gradientBrush = if (colors.isDark) {
        Brush.verticalGradient(
            colors = listOf(
                movie.primaryColor.copy(alpha = 0.5f),
                colors.surfaceVariant,
                colors.surface
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                movie.primaryColor.copy(alpha = 0.15f),
                colors.surfaceVariant,
                colors.surface
            )
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, colors.border, RoundedCornerShape(4.dp))
            .background(gradientBrush)
            .padding(12.dp)
    ) {
        // Decorative center icon
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(if (isWide) 48.dp else 64.dp)
                .clip(CircleShape)
                .background(colors.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "▶",
                fontSize = if (isWide) 18.sp else 24.sp,
                color = colors.primary
            )
        }

        // Top badges row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
                    .background(colors.primary)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = movie.format,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(3.dp))
                    .background(colors.pillBackground)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "★ ${movie.rating}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )
            }
        }

        // Bottom info
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        ) {
            Text(
                text = movie.genre.uppercase(),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = colors.textMuted
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = movie.title,
                fontSize = if (isWide) 16.sp else 14.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "${movie.duration} • ${movie.certificate}",
                fontSize = 11.sp,
                color = colors.textSecondary
            )
        }
    }
}
