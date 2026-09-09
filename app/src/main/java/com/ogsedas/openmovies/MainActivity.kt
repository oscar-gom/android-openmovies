package com.ogsedas.openmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ogsedas.openmovies.model.UserProfile
import com.ogsedas.openmovies.navigation.MainTab
import com.ogsedas.openmovies.navigation.Screen
import com.ogsedas.openmovies.ui.components.BottomNavBar
import com.ogsedas.openmovies.ui.screens.LoginScreen
import com.ogsedas.openmovies.ui.screens.MoviesScreen
import com.ogsedas.openmovies.ui.screens.ProfileScreen
import com.ogsedas.openmovies.ui.screens.SeatsScreen
import com.ogsedas.openmovies.ui.screens.ShowtimesScreen
import com.ogsedas.openmovies.ui.screens.StripeCheckoutScreen
import com.ogsedas.openmovies.ui.theme.AppTheme
import com.ogsedas.openmovies.ui.theme.OpenmoviesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenmoviesTheme {
                OpenMoviesApp()
            }
        }
    }
}

@Composable
fun OpenMoviesApp() {
    val colors = AppTheme.colors
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }
    var currentTab by remember { mutableStateOf(MainTab.MOVIES) }
    var userProfile by remember { mutableStateOf(UserProfile()) }

    val showBottomBar = currentScreen is Screen.Movies || currentScreen is Screen.Profile

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        currentScreen = when (tab) {
                            MainTab.MOVIES -> Screen.Movies
                            MainTab.PROFILE -> Screen.Profile
                        }
                    }
                )
            }
        },
        containerColor = colors.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Login -> {
                    LoginScreen(
                        onSignInSuccess = {
                            currentTab = MainTab.MOVIES
                            currentScreen = Screen.Movies
                        }
                    )
                }

                is Screen.Movies -> {
                    MoviesScreen(
                        onMovieSelected = { movie ->
                            currentScreen = Screen.Showtimes(movie)
                        }
                    )
                }

                is Screen.Showtimes -> {
                    ShowtimesScreen(
                        movie = screen.movie,
                        onBackClick = {
                            currentScreen = Screen.Movies
                        },
                        onProceedToSeats = { movie, date, slot ->
                            currentScreen = Screen.Seats(movie, date, slot)
                        }
                    )
                }

                is Screen.Seats -> {
                    SeatsScreen(
                        movie = screen.movie,
                        date = screen.date,
                        slot = screen.slot,
                        onBackClick = {
                            currentScreen = Screen.Showtimes(screen.movie)
                        },
                        onProceedToStripe = { movie, date, slot, seats, totalPrice ->
                            currentScreen = Screen.StripeCheckout(
                                movie = movie,
                                date = date,
                                slot = slot,
                                seats = seats,
                                totalPrice = totalPrice
                            )
                        }
                    )
                }

                is Screen.StripeCheckout -> {
                    StripeCheckoutScreen(
                        movie = screen.movie,
                        date = screen.date,
                        slot = screen.slot,
                        seats = screen.seats,
                        totalPrice = screen.totalPrice,
                        onBackClick = {
                            currentScreen = Screen.Seats(screen.movie, screen.date, screen.slot)
                        },
                        onPaymentSuccess = { newTicket ->
                            userProfile = userProfile.copy(
                                activeTickets = listOf(newTicket) + userProfile.activeTickets
                            )
                            currentTab = MainTab.PROFILE
                            currentScreen = Screen.Profile
                        }
                    )
                }

                is Screen.Profile -> {
                    ProfileScreen(
                        userProfile = userProfile,
                        onSignOut = {
                            currentScreen = Screen.Login
                        }
                    )
                }
            }
        }
    }
}