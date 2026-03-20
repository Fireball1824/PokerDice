package com.example.pokerdice.title


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokerdice.ui.theme.AppTheme
import com.example.pokerdice.views.title.TitleView

@Composable
fun TitleScreen(
    onStartMatch: () -> Unit,
    onStatistics: () -> Unit,
    onAbout: () -> Unit,
) {

    TitleView(
        onStartMatch = onStartMatch,
        onStatistics = onStatistics,
        onAbout = onAbout
    )
}

@Preview
@Composable
fun TitleScreenPreview() {
    AppTheme {
        TitleScreen(
            onStartMatch = {},
            onStatistics = {},
            onAbout = {}
        )
    }
}