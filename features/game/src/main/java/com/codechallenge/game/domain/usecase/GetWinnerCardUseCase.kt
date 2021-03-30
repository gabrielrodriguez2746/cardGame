package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.PriorityRepository
import com.codechallenge.game.domain.helpers.CardTieBreaker
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.PlayerCard
import javax.inject.Inject

class GetWinnerCardUseCase @Inject constructor(
    private val suitsPriorityRepository: PriorityRepository,
    private val cardTieBreaker: CardTieBreaker
) {

    suspend fun execute(playerCards: Pair<PlayerCard, PlayerCard>): Pair<PlayedCard, PlayedCard> {
        val (playerOneCard, playerTwoCard) = playerCards
        return when {
            playerOneCard.value > playerTwoCard.value -> PlayedCard.WinnerCard(playerOneCard) to PlayedCard.LooserCard(playerTwoCard)
            playerOneCard.value < playerTwoCard.value -> PlayedCard.LooserCard(playerOneCard) to PlayedCard.WinnerCard(playerTwoCard)
            else -> with(cardTieBreaker) { playerCards.tieBreak(suitsPriorityRepository.getSuitsPriority()) }
        }
    }
}
