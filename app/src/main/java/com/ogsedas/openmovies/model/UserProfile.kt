package com.ogsedas.openmovies.model

data class UserTicket(
    val id: String,
    val movieTitle: String,
    val cinemaHall: String,
    val dateTime: String,
    val seats: List<String>,
    val format: String,
    val qrCode: String,
    val price: Double,
    val isActive: Boolean = true
)

data class UserProfile(
    val name: String = "Oscar G. Sedas",
    val email: String = "oscar@openmovies.app",
    val activeTickets: List<UserTicket> = listOf(
        UserTicket(
            id = "OM-TKT-9901",
            movieTitle = "Interstellar Odyssey",
            cinemaHall = "Hall 01 • Laser 4K",
            dateTime = "Today, Sep 09 • 20:30 PM",
            seats = listOf("D3", "D4"),
            format = "Laser 4K",
            qrCode = "OM-9901-D3D4-SECURE",
            price = 25.00,
            isActive = true
        ),
        UserTicket(
            id = "OM-TKT-8422",
            movieTitle = "Chronicles of Dune",
            cinemaHall = "Hall 02 • Dolby Atmos",
            dateTime = "Fri, Sep 11 • 18:15 PM",
            seats = listOf("E5", "E6"),
            format = "Dolby Atmos",
            qrCode = "OM-8422-E5E6-SECURE",
            price = 28.00,
            isActive = true
        )
    ),
    val pastTickets: List<UserTicket> = listOf(
        UserTicket(
            id = "OM-TKT-7120",
            movieTitle = "Neon Velocity",
            cinemaHall = "Hall 04 • Standard",
            dateTime = "Sun, Aug 28 • 19:00 PM",
            seats = listOf("C4"),
            format = "Standard",
            qrCode = "OM-7120-C4-EXPIRED",
            price = 12.50,
            isActive = false
        ),
        UserTicket(
            id = "OM-TKT-6504",
            movieTitle = "The Silent Echo",
            cinemaHall = "Hall 01 • Laser 4K",
            dateTime = "Fri, Aug 12 • 21:30 PM",
            seats = listOf("B5", "B6"),
            format = "Laser 4K",
            qrCode = "OM-6504-B5B6-EXPIRED",
            price = 25.00,
            isActive = false
        )
    )
)
