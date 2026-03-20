package com.example.pokerdice.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokerdice.domain.dice.DiceHand
import com.example.pokerdice.domain.game.*
import com.example.pokerdice.domain.player.Player
import com.example.pokerdice.repositories.StatsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameConfigs: GameConfigs,
    private val service: GameService,
    private val stats: StatsRepository,
    playerNames: List<String>
) : ViewModel() {
    sealed class GameUiState {
        data class Loading(val message: String? = null) : GameUiState()

        data class Playing(
            val game: Game,
            val results: List<PlayerRollResult> = emptyList()
        ) : GameUiState()

        data class RoundResultState(
            val roundResult: RoundResult
        ) : GameUiState()

        data class MatchEnded(
            val winner: Player?,
            val rounds: List<RoundResult>
        ) : GameUiState()
    }

    private var game = Game(
        configs = gameConfigs,
        players = playerNames.mapIndexed { idx, name -> Player(idx, name) }
    )

    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = GameUiState.Loading("Starting game...")
            delay(1000)
            _uiState.value = GameUiState.Playing(game)

            checkIfIsAITurn()
        }
    }

    fun rollDice() {
        viewModelScope.launch {
            if (game.rollsRemaining <= 0) return@launch

            game = game.copy(
                diceHand = service.rollDice(game.diceHand, game.holdIndices),
                rollsRemaining = game.rollsRemaining - 1
            )

            _uiState.value = GameUiState.Playing(game, currentResults())
        }
    }

    fun toggleHold(index: Int) {
        viewModelScope.launch {
            game = game.copy(
                holdIndices =
                    if (index in game.holdIndices)
                        game.holdIndices - index
                    else
                        game.holdIndices + index
            )

            _uiState.value = GameUiState.Playing(game, currentResults())
        }
    }

    fun endTurn() {
        viewModelScope.launch {
            val currentPlayer = game.players[game.currentPlayerIndex]
            val result = PlayerRollResult(
                playerId = currentPlayer.id,
                playerName = currentPlayer.name,
                dice = game.diceHand,
                handRank = service.evaluateHand(game.diceHand)
            )
            val updatedResults = game.playerResults + result

            // Show player's final hand before transition
            _uiState.value = GameUiState.Playing(game.copy(playerResults = updatedResults), updatedResults)
            delay(1500)

            if (updatedResults.size == 2) {
                val winnerId = service.compareHands(
                    updatedResults[0],
                    updatedResults[1]
                ).let {
                    when {
                        it > 0 -> updatedResults[0].playerId
                        it < 0 -> updatedResults[1].playerId
                        else -> null
                    }
                }

                val roundResult = RoundResult(
                    roundNumber = game.round,
                    winnerPlayerId = winnerId,
                    results = updatedResults
                )

                game = game.copy(
                    allRounds = game.allRounds + roundResult,
                    playerResults = updatedResults
                )

                _uiState.value = GameUiState.Loading("Calculating round results...")
                delay(1200)
                _uiState.value = GameUiState.RoundResultState(roundResult)
            } else {
                game = game.copy(
                    currentPlayerIndex = service.nextPlayer(game.currentPlayerIndex),
                    diceHand = DiceHand.roll(),
                    holdIndices = emptySet(),
                    rollsRemaining = 3,
                    playerResults = updatedResults
                )
                _uiState.value = GameUiState.Playing(game, updatedResults)
                checkIfIsAITurn()
            }
        }
    }

    fun startNextRound() {
        viewModelScope.launch {
            if (game.round >= game.configs.rounds) {
                val winCounts = game.players.associateWith { p ->
                    game.allRounds.count { it.winnerPlayerId == p.id }
                }
                val maxWins = winCounts.maxOf { it.value }
                val winners = winCounts.filterValues { it == maxWins }.keys.toList()
                val winner: Player? = if (winners.size == 1) winners[0] else null

                updateAiStats(winner)
                _uiState.value = GameUiState.Loading("Final results...")
                delay(1500)
                _uiState.value = GameUiState.MatchEnded(
                    winner = winner,
                    rounds = game.allRounds
                )
                return@launch
            }
            val nextStartingPlayer = service.nextPlayer(game.startingPlayerIndex)

            game = game.copy(
                round = game.round + 1,
                startingPlayerIndex = nextStartingPlayer,
                currentPlayerIndex = nextStartingPlayer,
                diceHand = DiceHand.roll(),
                holdIndices = emptySet(),
                rollsRemaining = 3,
                playerResults = emptyList()
            )

            _uiState.value = GameUiState.Loading("Starting next round...")
            delay(1000)
            _uiState.value = GameUiState.Playing(game)

            checkIfIsAITurn()
        }
    }

    private fun checkIfIsAITurn() {
        if (gameConfigs.mode == GameMode.HUMAN_VS_AI &&
            game.currentPlayerIndex == 1
        ) {
            viewModelScope.launch {
                aiPlayTurn()
            }
        }
    }

    private suspend fun aiPlayTurn() {
        _uiState.value = GameUiState.Loading("AI is thinking...")
        delay(1000)
        while (game.rollsRemaining > 0) {
            if (game.rollsRemaining < 3) {
                val holds = service.aiDecideHold(game.diceHand, game.rollsRemaining)
                game = game.copy(holdIndices = holds)
                _uiState.value = GameUiState.Playing(game, currentResults())
                delay(1500)
            }

            _uiState.value = GameUiState.Loading("AI is rolling...")
            delay(800)

            game = game.copy(
                diceHand = service.rollDice(game.diceHand, game.holdIndices),
                rollsRemaining = game.rollsRemaining - 1
            )

            _uiState.value = GameUiState.Playing(game, currentResults())
            delay(1500)
        }
        endTurn()
    }

    private fun updateAiStats(winner: Player?) {
        if (gameConfigs.mode != GameMode.HUMAN_VS_AI) return
        val aiPlayer = game.players[1]
        viewModelScope.launch {
            stats.updateStats { current ->
                when {
                    winner == null -> current.copy(
                        gamesPlayed = current.gamesPlayed + 1
                    )
                    winner.id == aiPlayer.id -> current.copy(
                        gamesPlayed = current.gamesPlayed + 1,
                        gamesWon = current.gamesWon + 1
                    )
                    else -> current.copy(
                        gamesPlayed = current.gamesPlayed + 1,
                        gamesLost = current.gamesLost + 1
                    )
                }
            }
        }
    }

    private fun currentResults(): List<PlayerRollResult> =
        (_uiState.value as? GameUiState.Playing)?.results ?: emptyList()

    companion object {
        fun getFactory(
            gameConfigs: GameConfigs,
            gameService: GameService,
            stats: StatsRepository,
            playerNames: List<String>
        ) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return GameViewModel(
                    gameConfigs,
                    gameService,
                    stats,
                    playerNames
                ) as T
            }
        }
    }
}