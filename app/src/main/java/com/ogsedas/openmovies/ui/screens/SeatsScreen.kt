package com.ogsedas.openmovies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ogsedas.openmovies.model.Movie
import com.ogsedas.openmovies.model.SampleSeats
import com.ogsedas.openmovies.model.Seat
import com.ogsedas.openmovies.model.SeatStatus
import com.ogsedas.openmovies.model.ShowtimeSlot
import com.ogsedas.openmovies.ui.components.CinemaScreenArc
import com.ogsedas.openmovies.ui.theme.AppTheme

@Composable
fun SeatsScreen(
    movie: Movie,
    date: String,
    slot: ShowtimeSlot,
    onBackClick: () -> Unit,
    onProceedToStripe: (Movie, String, ShowtimeSlot, List<Seat>, Double) -> Unit
) {
    val colors = AppTheme.colors
    val initialSeats = remember { SampleSeats.generateCinemaHall() }
    val seatStates = remember {
        mutableStateMapOf<String, SeatStatus>().apply {
            initialSeats.forEach { put(it.id, it.status) }
        }
    }

    val selectedSeats = initialSeats.filter { seatStates[it.id] == SeatStatus.SELECTED }
    val totalPrice = selectedSeats.sumOf { it.price }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        // Navigation Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "← Back",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primary,
                modifier = Modifier.clickable { onBackClick() }
            )

            Text(
                text = "${slot.hall.uppercase()} • ${slot.time}",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = colors.textMuted
            )
        }

        // Movie Title & Session Info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 4.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = colors.textPrimary,
                letterSpacing = (-0.5).sp
            )
            Text(
                text = "$date • ${slot.hall} • Standard $12.50",
                fontSize = 12.sp,
                color = colors.textSecondary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Architectural Screen Line
            CinemaScreenArc(hallLabel = "SCREEN // ${slot.hall.uppercase()}")

            Spacer(modifier = Modifier.height(24.dp))

            // Seating Matrix (Rows A to F, 8 columns)
            val rows = listOf("A", "B", "C", "D", "E", "F")
            rows.forEach { rowLetter ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = rowLetter,
                        color = colors.textPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(18.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    for (col in 1..8) {
                        val seatId = "$rowLetter$col"
                        val seat = initialSeats.find { it.id == seatId } ?: continue
                        val currentStatus = seatStates[seatId] ?: SeatStatus.AVAILABLE

                        // Seat block: Available = neutral, Selected = indigo, Occupied = RED
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 3.dp)
                                .size(30.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    when (currentStatus) {
                                        SeatStatus.SELECTED -> colors.seatSelected
                                        SeatStatus.OCCUPIED -> colors.seatOccupied // HIGH-CONTRAST RED
                                        SeatStatus.AVAILABLE -> colors.seatAvailable
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = when (currentStatus) {
                                        SeatStatus.SELECTED -> colors.primary
                                        SeatStatus.OCCUPIED -> colors.seatOccupied
                                        SeatStatus.AVAILABLE -> colors.borderStrong
                                    },
                                    shape = RoundedCornerShape(3.dp)
                                )
                                .clickable(enabled = currentStatus != SeatStatus.OCCUPIED) {
                                    seatStates[seatId] = if (currentStatus == SeatStatus.SELECTED) {
                                        SeatStatus.AVAILABLE
                                    } else {
                                        SeatStatus.SELECTED
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = col.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (currentStatus) {
                                    SeatStatus.SELECTED -> Color.White
                                    SeatStatus.OCCUPIED -> Color.White
                                    SeatStatus.AVAILABLE -> colors.textPrimary
                                }
                            )
                        }

                        // Middle aisle gap
                        if (col == 4) {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = rowLetter,
                        color = colors.textPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Clean Legend (No Balcony VIP, clear Red for Occupied)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .border(1.dp, colors.border, RoundedCornerShape(4.dp))
                    .background(colors.surface)
                    .padding(vertical = 10.dp, horizontal = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(colors.seatAvailable)
                                .border(1.dp, colors.borderStrong, RoundedCornerShape(2.dp))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Available", fontSize = 11.sp, color = colors.textSecondary)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(colors.seatSelected)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Selected", fontSize = 11.sp, color = colors.textSecondary)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(colors.seatOccupied)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Occupied",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.seatOccupied
                        )
                    }
                }
            }
        }

        // Bottom Confirmation Drawer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, colors.border, RoundedCornerShape(0.dp))
                .background(colors.surface)
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = if (selectedSeats.isEmpty()) "No seats selected" else "${selectedSeats.size} Seat(s) Selected",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (selectedSeats.isEmpty()) {
                                "Tap seats in the hall above"
                            } else {
                                "Seats: " + selectedSeats.joinToString(", ") { it.id }
                            },
                            fontSize = 12.sp,
                            color = colors.textSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = String.format("$%.2f", totalPrice),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = colors.primary
                        )
                        Text(
                            text = "Total Price",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.textMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        onProceedToStripe(movie, date, slot, selectedSeats, totalPrice)
                    },
                    enabled = selectedSeats.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (selectedSeats.isEmpty()) "Select at least 1 seat" else "Continue to Stripe Checkout →",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
