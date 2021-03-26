package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.domain.formatter.GameStateFormatter
import com.codechallenge.game.domain.model.Player
import javax.inject.Inject

class UpdateGameStateUseCase @Inject constructor(
    private val gameStateFormatter: GameStateFormatter,
    private val gameStateRepository: GameStateRepository
) {

    fun execute(players: Pair<Player, Player>) {
        return with(gameStateFormatter) { gameStateRepository.updateGameState(players.toGameState()) }
    }
}
