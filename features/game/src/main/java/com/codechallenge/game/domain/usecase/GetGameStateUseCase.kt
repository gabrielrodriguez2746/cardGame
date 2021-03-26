package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.domain.formatter.GameStateFormatter
import com.codechallenge.game.domain.model.Player
import javax.inject.Inject

class GetGameStateUseCase @Inject constructor(
    private val gameStateFormatter: GameStateFormatter,
    private val gameStateRepository: GameStateRepository
) {

    fun execute(): Pair<Player, Player> {
        return with(gameStateFormatter) { gameStateRepository.getGameState().toPlayers() }
    }
}
