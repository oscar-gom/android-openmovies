package com.ogsedas.openmovies.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val MaterialLightColors = lightColorScheme(
    primary = EditorialLightColors.primary,
    onPrimary = EditorialLightColors.onPrimary,
    background = EditorialLightColors.background,
    surface = EditorialLightColors.surface,
    onBackground = EditorialLightColors.textPrimary,
    onSurface = EditorialLightColors.textPrimary
)

private val MaterialDarkColors = darkColorScheme(
    primary = EditorialDarkColors.primary,
    onPrimary = EditorialDarkColors.onPrimary,
    background = EditorialDarkColors.background,
    surface = EditorialDarkColors.surface,
    onBackground = EditorialDarkColors.textPrimary,
    onSurface = EditorialDarkColors.textPrimary
)

@Composable
fun OpenmoviesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val editorialColors = if (darkTheme) EditorialDarkColors else EditorialLightColors
    val materialColors = if (darkTheme) MaterialDarkColors else MaterialLightColors

    CompositionLocalProvider(LocalEditorialColors provides editorialColors) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = Typography,
            content = content
        )
    }
}

object AppTheme {
    val colors: EditorialColors
        @Composable
        @ReadOnlyComposable
        get() = LocalEditorialColors.current
}