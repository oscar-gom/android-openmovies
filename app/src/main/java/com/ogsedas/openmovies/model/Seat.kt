package com.ogsedas.openmovies.model

enum class SeatType(val displayName: String, val basePrice: Double) {
    STANDARD("Standard", 12.50),
    ACCESSIBLE("Accessible", 12.50)
}

enum class SeatStatus {
    AVAILABLE,
    SELECTED,
    OCCUPIED
}

data class Seat(
    val id: String,
    val row: String,
    val col: Int,
    val type: SeatType = SeatType.STANDARD,
    val status: SeatStatus = SeatStatus.AVAILABLE,
    val price: Double = type.basePrice
)

object SampleSeats {
    fun generateCinemaHall(): List<Seat> {
        val rows = listOf("A", "B", "C", "D", "E", "F")
        val cols = 8
        val list = mutableListOf<Seat>()

        // Predefined occupied seats shown in high-contrast RED
        val occupiedIds = setOf("B3", "B4", "C4", "C5", "D5", "D6", "E3")

        for (r in rows) {
            for (c in 1..cols) {
                val id = "$r$c"
                val type = if (r == "A" && (c == 1 || c == 8)) SeatType.ACCESSIBLE else SeatType.STANDARD
                val status = if (occupiedIds.contains(id)) SeatStatus.OCCUPIED else SeatStatus.AVAILABLE
                list.add(Seat(id = id, row = r, col = c, type = type, status = status))
            }
        }
        return list
    }
}
