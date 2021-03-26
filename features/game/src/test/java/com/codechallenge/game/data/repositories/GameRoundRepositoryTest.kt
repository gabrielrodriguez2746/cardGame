package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.Round
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GameRoundRepositoryTest {

    private lateinit var repository: GameRoundRepository

    @BeforeEach
    fun setUp() {
        repository = GameRoundRepository()
    }

    @Test
    fun `GIVEN add round WHEN getGameSummary THEN contains round`() {
        val round = mockk<Round>()
        repository.addRound(round)
        Assertions.assertTrue { repository.getGameSummary().contains(round) }
    }

    @Test
    fun `GIVEN no round WHEN getGameSummary THEN summary is empty`() {
        Assertions.assertTrue { repository.getGameSummary().isEmpty() }
    }

    @Test
    fun `GIVEN round added WHEN clean THEN summary is empty`() {
        val round = mockk<Round>()
        repository.addRound(round)

        repository.clean()

        repository.getGameSummary().size shouldBe 0
    }
}
