package com.example.pokerdice.title

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokerdice.about.AboutActivity
import com.example.pokerdice.matchSetup.MatchSetupActivity
import com.example.pokerdice.stats.StatsActivity
import com.example.pokerdice.ui.theme.AppTheme

class TitleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                TitleScreen(
                    onStartMatch = {
                        startActivity(Intent(applicationContext, MatchSetupActivity::class.java))
                    },
                    onStatistics = {
                        startActivity(Intent(applicationContext, StatsActivity::class.java))
                    },
                    onAbout = {
                        startActivity(Intent(applicationContext, AboutActivity::class.java))
                    }
                )
            }
        }
    }
}