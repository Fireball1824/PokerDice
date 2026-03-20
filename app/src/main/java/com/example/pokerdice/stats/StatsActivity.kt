package com.example.pokerdice.stats

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokerdice.title.TitleActivity
import com.example.pokerdice.ui.theme.AppTheme

class StatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                StatsScreen(
                    onBack = {
                        startActivity(Intent(applicationContext, TitleActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}