package com.example.pokerdice.game

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.pokerdice.domain.game.GameConfigs
import com.example.pokerdice.repositories.StatsRepository
import com.example.pokerdice.title.TitleActivity

class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = GameService()
        val configs = intent.getParcelableExtra<GameConfigs>("config")!!
        val player1 = configs.player1
        val player2 = configs.player2
        val playerNames = listOf(player1, player2)
        val stats = StatsRepository(applicationContext)
        val viewModel: GameViewModel by viewModels {
            GameViewModel.getFactory(configs,service, stats, playerNames)
        }
        setContent {
            GameScreen(
                viewModel = viewModel,
                onGameEnd = { startActivity(Intent(applicationContext, TitleActivity::class.java))
                    finish()
                }
            )
        }
    }
}