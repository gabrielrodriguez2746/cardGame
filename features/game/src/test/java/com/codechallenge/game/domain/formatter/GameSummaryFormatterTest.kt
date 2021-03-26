package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.data.model.Round
import com.codechallenge.game.domain.model.GameSummaryRound
import com.codechallenge.game.domain.model.PlayerCard
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GameSummaryFormatterTest {

    private val playerCardFormatter: PlayerCardFormatter = mockk(relaxed = true)

    private lateinit var formatter: GameSummaryFormatter

    @BeforeEach
    fun setup() {
        formatter = GameSummaryFormatter(playerCardFormatter)
    }

    @Test
    fun `GIVEN PlayerOneRound WHEN toSummaryRound THEN get PlayerOneRound`() {

        val playerCard1 = mockk<PlayerCard>()
        val pokerCard1 = mockk<PokerCard>()
        val playerCard2 = mockk<PlayerCard>()
        val pokerCard2 = mockk<PokerCard>()

        every { with(playerCardFormatter) { pokerCard1.toPlayerCard() } } returns playerCard1
        every { with(playerCardFormatter) { pokerCard2.toPlayerCard() } } returns playerCard2

        val input = Round.PlayerOneRound(pokerCard1, pokerCard2)
        val expected = GameSummaryRound.PlayerOneRound(playerCard1, playerCard2)

        with(formatter) {
            input.toSummaryRound() shouldBe expected
        }
    }

    @Test
    fun `GIVEN PlayerTwoRound WHEN toSummaryRound THEN get PlayerTwoRound`() {
        val playerCard1 = mockk<PlayerCard>()
        val pokerCard1 = mockk<PokerCard>()
        val playerCard2 = mockk<PlayerCard>()
        val pokerCard2 = mockk<PokerCard>()

        every { with(playerCardFormatter) { pokerCard1.toPlayerCard() } } returns playerCard1
        every { with(playerCardFormatter) { pokerCard2.toPlayerCard() } } returns playerCard2

        val input = Round.PlayerTwoRound(pokerCard1, pokerCard2)
        val expected = GameSummaryRound.PlayerTwoRound(playerCard1, playerCard2)

        with(formatter) {
            input.toSummaryRound() shouldBe expected
        }
    }

    @Test
    fun `WHEN toPoints THEN get expected`() {
        forAll(
            row(listOf<Round>(mockk<Round.PlayerOneRound>()), 2 to 0),
            row(listOf<Round>(mockk<Round.PlayerTwoRound>()), 0 to 2),
            row(listOf<Round>(mockk<Round.PlayerOneRound>(), mockk<Round.PlayerTwoRound>()), 2 to 2),
        ) { input, expected ->
            with(formatter) { input.toPoints() } shouldBe expected
        }
    }
}
