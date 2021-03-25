package com.codechallenge.game.domain.helpers

import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class PlayerCardSplitterTest {

    private val cardSplitter = PlayerCardSplitter()

    @Test
    fun `GIVEN list of cards WHEN split THEN get expected`() {
        val card1 = PlayerCard(1, "A", PlayerCardSuit.Hearts)
        val card2 = PlayerCard(2, "K", PlayerCardSuit.Diamond)
        val card3 = PlayerCard(3, "3", PlayerCardSuit.Club)
        val card4 = PlayerCard(4, "5", PlayerCardSuit.Spades)
        val input = listOf(card1, card2, card3, card4)

        val expected = listOf(card1, card3) to listOf(card2, card4)

        with(cardSplitter) {
            input.split() shouldBe expected
        }
    }
}
