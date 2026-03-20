package com.example.pokerdice.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokerdice.views.about.AboutView
import com.example.pokerdice.ui.theme.AppTheme

sealed class AboutScreenNavigationIntent {
    class Link(val destination: String) : AboutScreenNavigationIntent()
    object NavigateBack : AboutScreenNavigationIntent()
    object Email : AboutScreenNavigationIntent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    onNavigate: (AboutScreenNavigationIntent) -> Unit = { }
) {
    AppTheme {
        AboutView(
            modifier = modifier.fillMaxSize(),
            onNavigate = onNavigate
        )
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    AppTheme {
        AboutScreen(
            modifier = Modifier.fillMaxSize(),
            onNavigate = {}
        )
    }
}
