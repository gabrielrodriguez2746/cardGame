package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.data.model.Round
import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.domain.formatter.PlayedCardFormatter
import com.codechallenge.game.domain.model.PlayedCard
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UpdateGameRoundResultUseCaseTest {

    private val gameRoundRepository: GameRoundRepository = mockk(relaxUnitFun = true)
    private val playedCardFormatter: PlayedCardFormatter = mockk()

    private lateinit var useCase: UpdateGameRoundResultUseCase

    @BeforeEach
    fun setUp() {
        useCase = UpdateGameRoundResultUseCase(gameRoundRepository, playedCardFormatter)
    }

    @Test
    fun `GIVEN different winner combinations WHEN execute THEN save round with expected`() {
        val winnerCard = mockk<PlayedCard.WinnerCard>()
        val winnerPokerCard = mockk<PokerCard>()
        val looserCard = mockk<PlayedCard.LooserCard>()
        val looserPokerCard = mockk<PokerCard>()

        every { with(playedCardFormatter) { winnerCard.toPokerCard() } } returns winnerPokerCard
        every { with(playedCardFormatter) { looserCard.toPokerCard() } } returns looserPokerCard

        forAll(
            row(winnerCard to looserCard, Round.PlayerOneRound(winnerPokerCard, looserPokerCard)),
            row(looserCard to winnerCard, Round.PlayerTwoRound(winnerPokerCard, looserPokerCard)),
        ) { input, expected ->
            runBlocking { useCase.execute(input) }
            coVerify { gameRoundRepository.addRound(expected) }
        }
    }

    @Test
    fun `GIVEN invalid two winner state WHEN execute THEN throw exception`() {
        val winnerCard = mockk<PlayedCard.WinnerCard>()

        shouldThrow<IllegalArgumentException> {
            runBlocking { useCase.execute(winnerCard to winnerCard) }
        }
    }

    @Test
    fun `GIVEN invalid two looser state WHEN execute THEN throw exception`() {
        val looserCard = mockk<PlayedCard.LooserCard>()

        shouldThrow<IllegalArgumentException> {
            runBlocking { useCase.execute(looserCard to looserCard) }
        }
    }
}
