package com.example.pokerdice.matchSetup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokerdice.game.GameActivity
import com.example.pokerdice.title.TitleActivity
import com.example.pokerdice.ui.theme.AppTheme

class MatchSetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MatchSetupScreen(
                    onStartMatch = { config ->
                        val intent = Intent(applicationContext, GameActivity::class.java).apply {
                            putExtra("config", config)
                        }
                        startActivity(intent)
                    },
                    onCancel = {
                        startActivity(Intent(applicationContext, TitleActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}