package com.example.pokerdice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = neonGreen,
    onPrimary = Color.Black,
    secondary = buttonGreen,
    onSecondary = Color.Black,
    background = darkGrey,
    surface = almostBlack,
    onBackground = Color.White,
    onSurface = Color.White,
    error = red,
    onError = Color.Black
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}