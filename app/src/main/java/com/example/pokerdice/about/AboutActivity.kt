package com.example.pokerdice.about


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.example.pokerdice.ui.theme.AppTheme

class AboutActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AboutScreen(
                        onNavigate = { handleNavigation(it) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun handleNavigation(intent: AboutScreenNavigationIntent) {
        when (intent) {
            is AboutScreenNavigationIntent.Link -> navigateToURL(intent.destination)
            is AboutScreenNavigationIntent.Email -> navigateToEmail()
            AboutScreenNavigationIntent.NavigateBack -> finish()
        }
    }

    private fun navigateToURL(destination: String) {
        val webpage: Uri = destination.toUri()
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

    private fun navigateToEmail() {
        val recipients = arrayOf("A51607@alunos.isel.pt", "A51562@alunos.isel.pt", "A50503@alunos.isel.pt")
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, recipients)
        }
        startActivity(intent)
    }
}


