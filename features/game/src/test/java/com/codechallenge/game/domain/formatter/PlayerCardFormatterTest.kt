package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.model.CardValue
import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PlayerCardFormatterTest {

    private val cardValueFormatter: CardValueFormatter = mockk()
    private val suitsCardFormatter: SuitsCardFormatter = mockk()

    private lateinit var formatter: PlayerCardFormatter

    @BeforeEach
    fun setUp() {
        formatter = PlayerCardFormatter(cardValueFormatter, suitsCardFormatter)
    }

    @Test
    fun `WHEN toPlayerCard THEN get expected`() {
        val inputCardValue = CardValue.ACE
        val inputCardSuit = CardSuit.HEARTS
        val input = PokerCard(inputCardValue, inputCardSuit)
        every { with(cardValueFormatter) { inputCardValue.toLabel() } } returns "A"
        every { with(cardValueFormatter) { inputCardValue.toIntValue() } } returns 13
        every { with(suitsCardFormatter) { inputCardSuit.toPlayerCardSuit() } } returns PlayerCardSuit.Hearts

        with(formatter) { input.toPlayerCard() } shouldBe PlayerCard(13, "A", PlayerCardSuit.Hearts)
    }

    @Test
    fun `WHEN toPokerCard THEN get expected`() {
        val inputCardValue = 13
        val inputCardSuit = PlayerCardSuit.Hearts
        val input = mockk<PlayerCard> {
            every { value } returns inputCardValue
            every { suit } returns inputCardSuit
        }
        every { with(cardValueFormatter) { inputCardValue.toCardValue() } } returns CardValue.ACE
        every { with(suitsCardFormatter) { inputCardSuit.toCardSuit() } } returns CardSuit.HEARTS

        with(formatter) { input.toPokerCard() } shouldBe PokerCard(CardValue.ACE, CardSuit.HEARTS)
    }
}
