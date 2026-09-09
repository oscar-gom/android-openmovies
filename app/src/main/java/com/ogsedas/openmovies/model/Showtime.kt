package com.ogsedas.openmovies.model

data class ShowtimeSlot(
    val id: String,
    val time: String,
    val hall: String,
    val format: String,
    val price: Double = 12.50
)

data class CinemaDate(
    val id: String,
    val fullLabel: String, // e.g., "TODAY 09"
    val dayName: String,   // e.g., "TODAY"
    val dayNumber: String  // e.g., "09"
)

object SampleShowtimes {
    val dates = listOf(
        CinemaDate("d1", "TODAY 09", "TODAY", "09"),
        CinemaDate("d2", "THU 10", "THU", "10"),
        CinemaDate("d3", "FRI 11", "FRI", "11"),
        CinemaDate("d4", "SAT 12", "SAT", "12"),
        CinemaDate("d5", "SUN 13", "SUN", "13")
    )

    fun getSlotsForMovie(movieId: String): List<ShowtimeSlot> {
        return listOf(
            ShowtimeSlot("s1", "12:30 PM", "Hall 04", "Laser 4K", 12.50),
            ShowtimeSlot("s2", "15:45 PM", "Hall 01", "IMAX Laser", 15.00),
            ShowtimeSlot("s3", "18:15 PM", "Hall 04", "Laser 4K", 12.50),
            ShowtimeSlot("s4", "20:30 PM", "Hall 02", "Dolby Atmos", 14.00),
            ShowtimeSlot("s5", "22:45 PM", "Hall 01", "IMAX Laser", 15.00)
        )
    }
}
