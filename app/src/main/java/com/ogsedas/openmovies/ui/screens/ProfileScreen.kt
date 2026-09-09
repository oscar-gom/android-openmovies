package com.ogsedas.openmovies.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ogsedas.openmovies.model.UserProfile
import com.ogsedas.openmovies.model.UserTicket
import com.ogsedas.openmovies.ui.components.TicketCard
import com.ogsedas.openmovies.ui.components.TicketDetailsDialog
import com.ogsedas.openmovies.ui.theme.AppTheme

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onSignOut: () -> Unit
) {
    val colors = AppTheme.colors
    var activeTicketForModal by remember { mutableStateOf<UserTicket?>(null) }

    // Dialog showing full ticket details when an active ticket is tapped
    activeTicketForModal?.let { ticket ->
        TicketDetailsDialog(
            ticket = ticket,
            onDismiss = { activeTicketForModal = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // User Profile Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "OG",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = userProfile.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = colors.textPrimary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = userProfile.email,
                    fontSize = 13.sp,
                    color = colors.textSecondary,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // SECTION 1: Active Tickets
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ACTIVE TICKETS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primary,
                letterSpacing = 2.sp
            )
            Text(
                text = "${userProfile.activeTickets.size} upcoming",
                fontSize = 11.sp,
                color = colors.textMuted
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (userProfile.activeTickets.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(colors.surface)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No active reservations yet. Book your first cinema seats from the Billboard!",
                    fontSize = 13.sp,
                    color = colors.textSecondary
                )
            }
        } else {
            userProfile.activeTickets.forEach { ticket ->
                TicketCard(
                    ticket = ticket,
                    onTicketClick = { activeTicketForModal = it },
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SECTION 2: Booking History
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BOOKING HISTORY",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = colors.textMuted,
                letterSpacing = 2.sp
            )
            Text(
                text = "${userProfile.pastTickets.size} past events",
                fontSize = 11.sp,
                color = colors.textMuted
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        userProfile.pastTickets.forEach { ticket ->
            TicketCard(
                ticket = ticket,
                onTicketClick = { /* Past tickets do not show active QR */ },
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign out button
        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Sign Out",
                color = Color(0xFFEF4444),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
