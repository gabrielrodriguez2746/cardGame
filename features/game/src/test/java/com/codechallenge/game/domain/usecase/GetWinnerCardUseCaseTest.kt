package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.repositories.SuitsPriorityRepository
import com.codechallenge.game.domain.helpers.CardTieBreaker
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.PlayerCard
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetWinnerCardUseCaseTest {

    private val suitsPriorityRepository: SuitsPriorityRepository = mockk()
    private val cardTieBreaker: CardTieBreaker = mockk()

    private lateinit var useCase: GetWinnerCardUseCase

    @BeforeEach
    fun setup() {
        useCase = GetWinnerCardUseCase(suitsPriorityRepository, cardTieBreaker)
    }

    @Test
    fun `GIVEN tie cards WHEN execute THEN return tie breaker result`() {
        val card1 = getPlayerCard(1)
        val card2 = getPlayerCard(1)
        val tieCards = card1 to card2
        val suitsPriority = listOf<CardSuit>()
        val expected = PlayedCard.WinnerCard(card1) to PlayedCard.LooserCard(card2)

        every { suitsPriorityRepository.getSuitsPriority() } returns suitsPriority
        every { with(cardTieBreaker) { tieCards.tieBreak(suitsPriority) } } returns expected

        useCase.execute(card1 to card2) shouldBe expected
    }

    @Test
    fun `GIVEN no tie cards WHEN execute THEN get expected`() {
        val lowestCard = getPlayerCard(1)
        val highestCard = getPlayerCard(2)

        forAll(
            row(lowestCard to highestCard, PlayedCard.LooserCard(lowestCard) to PlayedCard.WinnerCard(highestCard)),
            row(highestCard to lowestCard, PlayedCard.WinnerCard(highestCard) to PlayedCard.LooserCard(lowestCard)),
        ) { input, expected ->
            useCase.execute(input) shouldBe expected
        }
    }

    private fun getPlayerCard(cardValue: Int): PlayerCard {
        return mockk {
            every { value } returns cardValue
        }
    }
}
