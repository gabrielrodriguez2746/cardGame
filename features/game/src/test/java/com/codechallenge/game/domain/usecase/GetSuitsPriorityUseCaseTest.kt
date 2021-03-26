package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.repositories.SuitsPriorityRepository
import com.codechallenge.game.domain.formatter.SuitsCardFormatter
import com.codechallenge.game.domain.model.PlayerCardSuit
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetSuitsPriorityUseCaseTest {

    private val suitsCardFormatter: SuitsCardFormatter = mockk()
    private val suitsPriorityRepository: SuitsPriorityRepository = mockk()

    private lateinit var useCase: GetSuitsPriorityUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetSuitsPriorityUseCase(suitsCardFormatter, suitsPriorityRepository)
    }

    @Test
    fun `WHEN execute THEN get expected`() {
        val suitsList = listOf(CardSuit.HEARTS, CardSuit.CLUB)
        val heart = PlayerCardSuit.Hearts
        val club = PlayerCardSuit.Club
        every { suitsPriorityRepository.getSuitsPriority() } returns suitsList
        every { with(suitsCardFormatter) { CardSuit.HEARTS.toPlayerCardSuit() } } returns heart
        every { with(suitsCardFormatter) { CardSuit.CLUB.toPlayerCardSuit() } } returns club

        useCase.execute() shouldBe listOf(heart, club)
    }
}
