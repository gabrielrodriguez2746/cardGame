package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.Round
import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.domain.formatter.GameSummaryFormatter
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetRoundSummaryPointsTest {

    private val gameSummaryFormatter: GameSummaryFormatter = mockk()
    private val gameRoundRepository: GameRoundRepository = mockk()

    private lateinit var useCase: GetRoundSummaryPoints

    @BeforeEach
    fun setup() {
        useCase = GetRoundSummaryPoints(gameSummaryFormatter, gameRoundRepository)
    }

    @Test
    fun `WHEN execute THEN get expected`() {
        val roundData = listOf<Round>(mockk(), mockk())
        val expected = 2 to 2
        coEvery { gameRoundRepository.getGameSummary() } returns roundData
        every { with(gameSummaryFormatter) { roundData.toPoints() } } returns expected

        runBlocking { useCase.execute() } shouldBe expected
    }
}
