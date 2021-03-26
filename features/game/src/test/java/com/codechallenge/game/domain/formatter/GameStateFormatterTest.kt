package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.GameState
import com.codechallenge.game.data.model.PlayersGameState.PlayerOneState
import com.codechallenge.game.data.model.PlayersGameState.PlayerTwoState
import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.domain.model.Player.PlayerOne
import com.codechallenge.game.domain.model.Player.PlayerTwo
import com.codechallenge.game.domain.model.PlayerCard
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GameStateFormatterTest {

    private val playerCardFormatter: PlayerCardFormatter = mockk()

    private lateinit var formatter: GameStateFormatter

    @BeforeEach
    fun setUp() {
        formatter = GameStateFormatter(playerCardFormatter)
    }

    @Test
    fun `WHEN toGameState THE get expected`() {
        val card1 = mockk<PlayerCard>()
        val pokerCard1 = mockk<PokerCard>()
        val playerOneDeck = listOf(card1)
        val card2 = mockk<PlayerCard>()
        val pokerCard2 = mockk<PokerCard>()
        val playerTwoDeck = listOf(card2)

        every { with(playerCardFormatter) { card1.toPokerCard() } } returns pokerCard1
        every { with(playerCardFormatter) { card2.toPokerCard() } } returns pokerCard2

        with(formatter) {
            (PlayerOne(playerOneDeck) to PlayerTwo(playerTwoDeck)).toGameState()
        } shouldBe GameState(
            listOf(
                PlayerOneState(listOf(pokerCard1)),
                PlayerTwoState(listOf(pokerCard2)),
            )
        )
    }

    @Test
    fun `WHEN toPlayers THEN get expected`() {
        val card1 = mockk<PlayerCard>()
        val pokerCard1 = mockk<PokerCard>()
        val playerOneDeck = listOf(pokerCard1)
        val card2 = mockk<PlayerCard>()
        val pokerCard2 = mockk<PokerCard>()
        val playerTwoDeck = listOf(pokerCard2)

        val gameState = GameState(listOf(PlayerOneState(playerOneDeck), PlayerTwoState(playerTwoDeck)))

        every { with(playerCardFormatter) { pokerCard1.toPlayerCard() } } returns card1
        every { with(playerCardFormatter) { pokerCard2.toPlayerCard() } } returns card2

        with(formatter) {
            gameState.toPlayers()
        } shouldBe (PlayerOne(listOf(card1)) to PlayerTwo(listOf(card2)))
    }
}
