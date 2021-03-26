package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.Round
import com.codechallenge.game.domain.NUMBER_PLAYERS
import com.codechallenge.game.domain.model.GameSummaryRound
import javax.inject.Inject

class GameSummaryFormatter @Inject constructor(
    private val playerCardFormatter: PlayerCardFormatter
) {

    fun Round.toSummaryRound(): GameSummaryRound {
        return when (this) {
            is Round.PlayerOneRound -> with(playerCardFormatter) {
                GameSummaryRound.PlayerOneRound(
                    this@toSummaryRound.playerOneCard.toPlayerCard(),
                    this@toSummaryRound.playerTwoCard.toPlayerCard()
                )
            }
            is Round.PlayerTwoRound -> with(playerCardFormatter) {
                GameSummaryRound.PlayerTwoRound(
                    this@toSummaryRound.playerOneCard.toPlayerCard(),
                    this@toSummaryRound.playerTwoCard.toPlayerCard()
                )
            }
        }
    }

    fun List<Round>.toPoints(): Pair<Int, Int> {
        return filterIsInstance<Round.PlayerOneRound>().size * NUMBER_PLAYERS to filterIsInstance<Round.PlayerTwoRound>().size * NUMBER_PLAYERS
    }
}
