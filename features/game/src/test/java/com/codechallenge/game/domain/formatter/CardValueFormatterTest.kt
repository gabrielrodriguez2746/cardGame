package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.CardValue
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CardValueFormatterTest {

    private val formatter: CardValueFormatter = CardValueFormatter()

    @Test
    fun `WHEN toInt THEN get expected`() {
        forAll(
            row(CardValue.ACE, 13),
            row(CardValue.KING, 12),
            row(CardValue.QUEEN, 11),
            row(CardValue.JACK, 10),
            row(CardValue.TEN, 9),
            row(CardValue.NINE, 8),
            row(CardValue.EIGHT, 7),
            row(CardValue.SEVEN, 6),
            row(CardValue.SIX, 5),
            row(CardValue.FIVE, 4),
            row(CardValue.FOUR, 3),
            row(CardValue.THREE, 2),
            row(CardValue.TWO, 1),
        ) { card, expected ->
            with(formatter) { card.toIntValue() } shouldBe expected
        }
    }

    @Test
    fun `WHEN toLabel THEN get expected`() {
        forAll(
            row(CardValue.ACE, "A"),
            row(CardValue.KING, "K"),
            row(CardValue.QUEEN, "Q"),
            row(CardValue.JACK, "J"),
            row(CardValue.TEN, "10"),
            row(CardValue.NINE, "9"),
            row(CardValue.EIGHT, "8"),
            row(CardValue.SEVEN, "7"),
            row(CardValue.SIX, "6"),
            row(CardValue.FIVE, "5"),
            row(CardValue.FOUR, "4"),
            row(CardValue.THREE, "3"),
            row(CardValue.TWO, "2"),
        ) { card, expected ->
            with(formatter) { card.toLabel() } shouldBe expected
        }
    }

    @Test
    fun `GIVEN values in range WHEN toCardValue THEN get expected`() {
        forAll(
            row(13, CardValue.ACE),
            row(12, CardValue.KING),
            row(11, CardValue.QUEEN),
            row(10, CardValue.JACK),
            row(9, CardValue.TEN),
            row(8, CardValue.NINE),
            row(7, CardValue.EIGHT),
            row(6, CardValue.SEVEN),
            row(5, CardValue.SIX),
            row(4, CardValue.FIVE),
            row(3, CardValue.FOUR),
            row(2, CardValue.THREE),
            row(1, CardValue.TWO),
        ) { card, expected ->
            with(formatter) { card.toCardValue() } shouldBe expected
        }
    }

    @Test
    fun `GIVEN value out of range WHEN toCardValue THEN throw exception`() {
        assertThrows<IllegalArgumentException> {
            with(formatter) { 14.toCardValue() }
        }
    }
}
