package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.repositories.SuitsPriorityRepository
import com.codechallenge.game.domain.helpers.CardTieBreaker
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.PlayerCard
import javax.inject.Inject

class GetWinnerCardUseCase @Inject constructor(
    private val suitsPriorityRepository: SuitsPriorityRepository,
    private val cardTieBreaker: CardTieBreaker
) {

    fun execute(playerCards: Pair<PlayerCard, PlayerCard>): Pair<PlayedCard, PlayedCard> {
        val (playerOneCard, playerTwoCard) = playerCards
        return when {
            playerOneCard.value > playerTwoCard.value -> PlayedCard.WinnerCard(playerOneCard) to PlayedCard.LooserCard(playerTwoCard)
            playerOneCard.value < playerTwoCard.value -> PlayedCard.WinnerCard(playerTwoCard) to PlayedCard.LooserCard(playerOneCard)
            else -> with(cardTieBreaker) { playerCards.tieBreak(suitsPriorityRepository.getSuitsPriority()) }
        }
    }
}
