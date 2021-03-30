package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.GameRepository
import com.codechallenge.game.data.repositories.RoundRepository
import com.codechallenge.game.domain.NUMBER_PLAYERS
import javax.inject.Inject

class HasGameEndUseCase @Inject constructor(
    private val roundRepository: RoundRepository,
    private val cardsGameRepository: GameRepository
) {

    suspend fun execute(): Boolean {
        return roundRepository.getGameSummary().size == cardsGameRepository.getSetOfCard().size / NUMBER_PLAYERS
    }
}
