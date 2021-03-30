package com.codechallenge.game.presentation

import android.content.Context
import com.codechallenge.game.R
import com.codechallenge.game.domain.model.GameSummary
import com.codechallenge.game.domain.model.GameSummaryRound
import com.codechallenge.game.domain.model.PlayerCardSuit
import javax.inject.Inject

class GameSummaryFormatter @Inject constructor() {

    fun GameSummary.toRoundString(context: Context): Array<String> {
        return rounds.toMutableList()
            .mapIndexed { index, gameSummaryRound ->
                val player = if (gameSummaryRound is GameSummaryRound.PlayerOneRound) {
                    context.getString(R.string.game_player_one)
                } else {
                    context.getString(R.string.game_player_two)
                }
                val playerOneCard = when (gameSummaryRound.playerOneCard.suit) {
                    PlayerCardSuit.Club -> {
                        context.getString(
                            R.string.game_summary_club_text,
                            gameSummaryRound.playerOneCard.value
                        )
                    }
                    PlayerCardSuit.Diamond -> {
                        context.getString(
                            R.string.game_summary_diamond_text,
                            gameSummaryRound.playerOneCard.value
                        )
                    }
                    PlayerCardSuit.Hearts -> {
                        context.getString(
                            R.string.game_summary_heart_text,
                            gameSummaryRound.playerOneCard.value
                        )
                    }
                    PlayerCardSuit.Spades -> {
                        context.getString(
                            R.string.game_summary_spade_text,
                            gameSummaryRound.playerOneCard.value
                        )
                    }
                }
                val playerTwoCard = when (gameSummaryRound.playerTwoCard.suit) {
                    PlayerCardSuit.Club -> {
                        context.getString(
                            R.string.game_summary_club_text,
                            gameSummaryRound.playerTwoCard.value
                        )
                    }
                    PlayerCardSuit.Diamond -> {
                        context.getString(
                            R.string.game_summary_diamond_text,
                            gameSummaryRound.playerTwoCard.value
                        )
                    }
                    PlayerCardSuit.Hearts -> {
                        context.getString(
                            R.string.game_summary_heart_text,
                            gameSummaryRound.playerTwoCard.value
                        )
                    }
                    PlayerCardSuit.Spades -> {
                        context.getString(
                            R.string.game_summary_spade_text,
                            gameSummaryRound.playerTwoCard.value
                        )
                    }
                }
                context.getString(
                    R.string.game_summary_text_item,
                    index + 1,
                    player,
                    playerOneCard,
                    playerTwoCard
                )
            }.toTypedArray()
    }
}
