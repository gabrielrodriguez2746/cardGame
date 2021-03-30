package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.Round
import com.codechallenge.game.data.repositories.RoundRepository
import com.codechallenge.game.domain.formatter.PlayedCardFormatter
import com.codechallenge.game.domain.model.PlayedCard
import javax.inject.Inject

class UpdateGameRoundResultUseCase @Inject constructor(
    private val gameRoundRepository: RoundRepository,
    private val playedCardFormatter: PlayedCardFormatter,
) {

    suspend fun execute(playedCards: Pair<PlayedCard, PlayedCard>) {
        gameRoundRepository.addRound(getRound(playedCards))
    }

    private fun getRound(playedCards: Pair<PlayedCard, PlayedCard>): Round {
        val (playerOneCard, playerTwoCard) = playedCards
        return with(playedCardFormatter) {
            when {
                playedCards.playerOneWins() -> Round.PlayerOneRound(
                    playerOneCard.toPokerCard(),
                    playerTwoCard.toPokerCard()
                )
                playedCards.playerTwoWins() -> Round.PlayerTwoRound(
                    playerTwoCard.toPokerCard(),
                    playerOneCard.toPokerCard()
                )
                else -> throw IllegalArgumentException("There is always a winner on each round")
            }
        }
    }

    private fun Pair<PlayedCard, PlayedCard>.playerOneWins(): Boolean {
        return first is PlayedCard.WinnerCard && second is PlayedCard.LooserCard
    }

    private fun Pair<PlayedCard, PlayedCard>.playerTwoWins(): Boolean {
        return first is PlayedCard.LooserCard && second is PlayedCard.WinnerCard
    }
}
