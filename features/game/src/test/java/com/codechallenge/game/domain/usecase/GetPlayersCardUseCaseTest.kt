package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.data.repositories.CardsGameRepository
import com.codechallenge.game.domain.formatter.PlayerCardFormatter
import com.codechallenge.game.domain.helpers.CardShuffler
import com.codechallenge.game.domain.helpers.PlayerCardSplitter
import com.codechallenge.game.domain.model.Player
import com.codechallenge.game.domain.model.PlayerCard
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetPlayersCardUseCaseTest {

    private val cardsGameRepository: CardsGameRepository = mockk()
    private val playerCardFormatter: PlayerCardFormatter = mockk()
    private val playerCardSplitter: PlayerCardSplitter = mockk()
    private val cardShuffler: CardShuffler = mockk()

    private lateinit var useCase: GetPlayersCardUseCase

    @BeforeEach
    fun setup() {
        useCase = GetPlayersCardUseCase(cardsGameRepository, playerCardFormatter, playerCardSplitter, cardShuffler)
    }

    @Test
    fun `WHEN execute THEN get two player`() {
        val expectedPlayer1Card = mockk<PlayerCard>()
        val expectedPlayer2Card = mockk<PlayerCard>()
        val expectedCards = listOf(expectedPlayer1Card, expectedPlayer2Card)
        val expectedPairOfCards = listOf(expectedPlayer1Card) to listOf(expectedPlayer2Card)
        val result = Player.PlayerOne(listOf(expectedPlayer1Card)) to Player.PlayerTwo(listOf(expectedPlayer2Card))

        val pokerCard1 = mockk<PokerCard>()
        val pokerCard2 = mockk<PokerCard>()
        val repositoryData = listOf(pokerCard1, pokerCard2)
        coEvery { cardsGameRepository.getSetOfCard() } returns repositoryData
        every { with(cardShuffler) { repositoryData.shuffleDeck() } } returns repositoryData
        every { with(playerCardFormatter) { pokerCard1.toPlayerCard() } } returns expectedPlayer1Card
        every { with(playerCardFormatter) { pokerCard2.toPlayerCard() } } returns expectedPlayer2Card
        every { with(playerCardSplitter) { expectedCards.split() } } returns expectedPairOfCards

        runBlocking { useCase.execute() } shouldBe result
    }
}
