package com.ogsedas.openmovies.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class EditorialColors(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val border: Color,
    val borderStrong: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val pillBackground: Color,
    val seatAvailable: Color,
    val seatSelected: Color,
    val seatOccupied: Color,
    val isDark: Boolean
)

val EditorialLightColors = EditorialColors(
    background = Color(0xFFF8F9FA),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFF1F4F9),
    border = Color(0xFFE2E8F0),
    borderStrong = Color(0xFFCBD5E1),
    primary = Color(0xFF3B50DF),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF10B981),
    textPrimary = Color(0xFF0F172A),
    textSecondary = Color(0xFF64748B),
    textMuted = Color(0xFF94A3B8),
    pillBackground = Color(0xFFEEF2FF),
    seatAvailable = Color(0xFFE2E8F0),
    seatSelected = Color(0xFF3B50DF),
    seatOccupied = Color(0xFFDC2626), // High-contrast Red
    isDark = false
)

val EditorialDarkColors = EditorialColors(
    background = Color(0xFF0E1117),
    surface = Color(0xFF181B22),
    surfaceVariant = Color(0xFF222631),
    border = Color(0xFF2E3440),
    borderStrong = Color(0xFF3E4656),
    primary = Color(0xFF5B6DF0), // High contrast indigo for dark mode
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF34D399),
    textPrimary = Color(0xFFF1F5F9),
    textSecondary = Color(0xFF94A3B8),
    textMuted = Color(0xFF64748B),
    pillBackground = Color(0xFF232838),
    seatAvailable = Color(0xFF2A303C),
    seatSelected = Color(0xFF5B6DF0),
    seatOccupied = Color(0xFFEF4444), // High-contrast Red in dark mode
    isDark = true
)

val LocalEditorialColors = staticCompositionLocalOf { EditorialLightColors }