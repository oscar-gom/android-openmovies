package com.ogsedas.openmovies.navigation

import com.ogsedas.openmovies.model.Movie
import com.ogsedas.openmovies.model.Seat
import com.ogsedas.openmovies.model.ShowtimeSlot

sealed class Screen {
    data object Login : Screen()
    data object Movies : Screen()
    data class Showtimes(val movie: Movie) : Screen()
    data class Seats(
        val movie: Movie,
        val date: String,
        val slot: ShowtimeSlot
    ) : Screen()
    data class StripeCheckout(
        val movie: Movie,
        val date: String,
        val slot: ShowtimeSlot,
        val seats: List<Seat>,
        val totalPrice: Double
    ) : Screen()
    data object Profile : Screen()
}

enum class MainTab(val title: String) {
    MOVIES("Billboard"),
    PROFILE("My Tickets")
}
