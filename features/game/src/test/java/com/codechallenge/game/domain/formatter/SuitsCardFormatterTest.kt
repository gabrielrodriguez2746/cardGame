package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.domain.model.PlayerCardSuit
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class SuitsCardFormatterTest {

    private val formatter = SuitsCardFormatter()

    @Test
    fun `WHEN toPlayerCardSuit THEN get expected`() {
        forAll(
            row(CardSuit.CLUB, PlayerCardSuit.Club),
            row(CardSuit.DIAMOND, PlayerCardSuit.Diamond),
            row(CardSuit.HEARTS, PlayerCardSuit.Hearts),
            row(CardSuit.SPADES, PlayerCardSuit.Spades),
        ) { input, expected ->
            with(formatter) { input.toPlayerCardSuit() shouldBe expected }
        }
    }

    @Test
    fun `WHEN toCardSuit THEN get expected`() {
        forAll(
            row(PlayerCardSuit.Club, CardSuit.CLUB),
            row(PlayerCardSuit.Diamond, CardSuit.DIAMOND),
            row(PlayerCardSuit.Hearts, CardSuit.HEARTS),
            row(PlayerCardSuit.Spades, CardSuit.SPADES),
        ) { input, expected ->
            with(formatter) { input.toCardSuit() shouldBe expected }
        }
    }
}
