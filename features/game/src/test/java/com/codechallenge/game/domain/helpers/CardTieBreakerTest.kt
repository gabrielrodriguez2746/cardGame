package com.codechallenge.game.domain.helpers

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.domain.formatter.SuitsCardFormatter
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class CardTieBreakerTest {

    private val tieBreaker = CardTieBreaker(SuitsCardFormatter())

    @Test
    fun `GIVEN pair of cards WHEN tieBreak THEN get expected`() {
        val spade = getPlayerCard(PlayerCardSuit.Spades)
        val heart = getPlayerCard(PlayerCardSuit.Hearts)
        val diamond = getPlayerCard(PlayerCardSuit.Diamond)
        val club = getPlayerCard(PlayerCardSuit.Club)
        forAll(
            row(spade, heart, listOf(CardSuit.SPADES), PlayedCard.WinnerCard(spade) to PlayedCard.LooserCard(heart)),
            row(spade, diamond, listOf(CardSuit.SPADES), PlayedCard.WinnerCard(spade) to PlayedCard.LooserCard(diamond)),
            row(spade, club, listOf(CardSuit.SPADES), PlayedCard.WinnerCard(spade) to PlayedCard.LooserCard(club)),
            row(heart, spade, listOf(CardSuit.HEARTS), PlayedCard.WinnerCard(heart) to PlayedCard.LooserCard(spade)),
            row(heart, diamond, listOf(CardSuit.HEARTS), PlayedCard.WinnerCard(heart) to PlayedCard.LooserCard(diamond)),
            row(heart, club, listOf(CardSuit.HEARTS), PlayedCard.WinnerCard(heart) to PlayedCard.LooserCard(club)),
            row(diamond, heart, listOf(CardSuit.DIAMOND), PlayedCard.WinnerCard(diamond) to PlayedCard.LooserCard(heart)),
            row(diamond, spade, listOf(CardSuit.DIAMOND), PlayedCard.WinnerCard(diamond) to PlayedCard.LooserCard(spade)),
            row(diamond, club, listOf(CardSuit.DIAMOND), PlayedCard.WinnerCard(diamond) to PlayedCard.LooserCard(club)),
            row(club, heart, listOf(CardSuit.CLUB), PlayedCard.WinnerCard(club) to PlayedCard.LooserCard(heart)),
            row(club, diamond, listOf(CardSuit.CLUB), PlayedCard.WinnerCard(club) to PlayedCard.LooserCard(diamond)),
            row(heart, club, listOf(CardSuit.CLUB), PlayedCard.LooserCard(heart) to PlayedCard.WinnerCard(club)),
        ) { card1, card2, suits, result ->
            with(tieBreaker) {
                (card1 to card2).tieBreak(suits) shouldBe result
            }
        }
    }

    private fun getPlayerCard(expected: PlayerCardSuit): PlayerCard {
        return mockk { every { suit } returns expected }
    }
}
