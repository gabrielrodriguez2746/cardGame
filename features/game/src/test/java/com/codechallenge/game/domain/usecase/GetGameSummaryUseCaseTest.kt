package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.Round
import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.domain.formatter.GameSummaryFormatter
import com.codechallenge.game.domain.model.GameSummary
import com.codechallenge.game.domain.model.GameSummaryRound
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetGameSummaryUseCaseTest {

    private val gameSummaryFormatter: GameSummaryFormatter = mockk()
    private val gameRoundRepository: GameRoundRepository = mockk()

    private lateinit var useCase: GetGameSummaryUseCase

    @BeforeEach
    fun setup() {
        useCase = GetGameSummaryUseCase(gameSummaryFormatter, gameRoundRepository)
    }

    @Test
    fun `GIVEN game summary list WHEN execute THEN get expected`() {
        val round1 = mockk<Round.PlayerOneRound>()
        val expectedRound1 = mockk<GameSummaryRound.PlayerOneRound>()
        val round2 = mockk<Round.PlayerTwoRound>()
        val expectedRound2 = mockk<GameSummaryRound.PlayerTwoRound>()

        every { gameRoundRepository.getGameSummary() } returns listOf(round1, round2)
        every { with(gameSummaryFormatter) { round1.toSummaryRound() } } returns expectedRound1
        every { with(gameSummaryFormatter) { round2.toSummaryRound() } } returns expectedRound2

        useCase.execute() shouldBe GameSummary(listOf(expectedRound1, expectedRound2))
    }
}
