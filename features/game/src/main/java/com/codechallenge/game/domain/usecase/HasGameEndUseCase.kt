package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.CardsGameRepository
import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.domain.NUMBER_PLAYERS
import javax.inject.Inject

class HasGameEndUseCase @Inject constructor(
    private val roundRepository: GameRoundRepository,
    private val cardsGameRepository: CardsGameRepository
) {

    fun execute(): Boolean {
        return roundRepository.getGameSummary().size == cardsGameRepository.getSetOfCard().size / NUMBER_PLAYERS
    }
}
