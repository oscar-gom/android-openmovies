package com.ogsedas.openmovies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ogsedas.openmovies.model.Movie
import com.ogsedas.openmovies.model.SampleMovies
import com.ogsedas.openmovies.model.SampleShowtimes
import com.ogsedas.openmovies.ui.components.DateSelector
import com.ogsedas.openmovies.ui.components.MovieListItem
import com.ogsedas.openmovies.ui.theme.AppTheme

@Composable
fun MoviesScreen(
    onMovieSelected: (Movie) -> Unit
) {
    val colors = AppTheme.colors
    var selectedDateId by remember { mutableStateOf(SampleShowtimes.dates.first().id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // Editorial Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PROGRAMME",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "SEPTEMBER 2026",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = colors.textMuted
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Film Selection",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = colors.textPrimary,
                letterSpacing = (-0.5).sp
            )
        }

        // Date Picker Carousel
        DateSelector(
            dates = SampleShowtimes.dates,
            selectedDateId = selectedDateId,
            onDateSelected = { selectedDateId = it.id },
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Movie List
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(SampleMovies.list) { index, movie ->
                MovieListItem(
                    index = index,
                    movie = movie,
                    onBookSeatsClick = { onMovieSelected(movie) }
                )
            }
        }
    }
}
