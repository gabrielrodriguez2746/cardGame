package com.codechallenge.game.presentation

import com.codechallenge.game.InstantExecutorExtension
import com.codechallenge.game.domain.model.GameSummary
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.Player
import com.codechallenge.game.domain.model.PlayerCard
import com.codechallenge.game.domain.model.PlayerCardSuit
import com.codechallenge.game.domain.usecase.GetGameStateUseCase
import com.codechallenge.game.domain.usecase.GetGameSummaryUseCase
import com.codechallenge.game.domain.usecase.GetPlayersCardUseCase
import com.codechallenge.game.domain.usecase.GetRoundSummaryPoints
import com.codechallenge.game.domain.usecase.GetSuitsPriorityUseCase
import com.codechallenge.game.domain.usecase.GetWinnerCardUseCase
import com.codechallenge.game.domain.usecase.HasGameEndUseCase
import com.codechallenge.game.domain.usecase.ResetGameStateUseCase
import com.codechallenge.game.domain.usecase.UpdateGameRoundResultUseCase
import com.codechallenge.game.domain.usecase.UpdateGameStateUseCase
import com.codechallenge.game.presentation.GameViewModel.GameState
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class GameViewModelTest {

    private val getPlayersCardUseCase: GetPlayersCardUseCase = mockk()
    private val getSuitsPriorityUseCase: GetSuitsPriorityUseCase = mockk()
    private val updateGameStateUseCase: UpdateGameStateUseCase = mockk()
    private val getGameStateUseCase: GetGameStateUseCase = mockk()
    private val getWinnerCardUseCase: GetWinnerCardUseCase = mockk()
    private val updateGameRoundResultUseCase: UpdateGameRoundResultUseCase = mockk()
    private val getRoundSummaryPoints: GetRoundSummaryPoints = mockk()
    private val resetGameStateUseCase: ResetGameStateUseCase = mockk()
    private val hasGameEndUseCase: HasGameEndUseCase = mockk()
    private val getGameSummaryUseCase: GetGameSummaryUseCase = mockk()

    private lateinit var viewModel: GameViewModel

    @BeforeEach
    fun setUp() {
        viewModel = GameViewModel(
            getPlayersCardUseCase,
            getSuitsPriorityUseCase,
            updateGameStateUseCase,
            getGameStateUseCase,
            getWinnerCardUseCase,
            updateGameRoundResultUseCase,
            getRoundSummaryPoints,
            resetGameStateUseCase,
            hasGameEndUseCase,
            getGameSummaryUseCase
        )
    }

    @Test
    fun `WHEN onStart THEN GameStarted state`() {
        val playerOne = mockk<Player>()
        val playerTwo = mockk<Player>()
        val players = playerOne to playerTwo
        val priorities = listOf<PlayerCardSuit>()
        every { updateGameStateUseCase.execute(any()) } just runs
        every { getPlayersCardUseCase.execute() } returns players
        every { getSuitsPriorityUseCase.execute() } returns priorities

        viewModel.onStart()

        viewModel.gameState.value shouldBe GameState.GameStarted(
            playerOne,
            playerTwo,
            priorities
        )
        verify(exactly = 1) { updateGameStateUseCase.execute(players) }
    }

    @Test
    fun `WHEN onHit THEN RoundPlayed state`() {
        val card1 = mockk<PlayerCard>()
        val card2 = mockk<PlayerCard>()
        val playerOne = Player.PlayerOne(listOf(card1))
        val playerTwo = Player.PlayerTwo(listOf(card2))
        val players = playerOne to playerTwo
        every { getGameStateUseCase.execute() } returns players

        viewModel.onHit()

        viewModel.gameState.value shouldBe GameState.RoundPlayed(card1, card2)
    }

    @Nested
    inner class OnRound {

        private val card1 = mockk<PlayerCard>()
        private val card2 = mockk<PlayerCard>()
        private val playerOne = Player.PlayerOne(listOf(card1))
        private val playerTwo = Player.PlayerTwo(listOf(card2))
        private val players = playerOne to playerTwo

        @BeforeEach
        fun setup() {
            every { getGameStateUseCase.execute() } returns players
        }

        @Test
        fun `GIVEN round for player one WHEN onRound THEN RoundWinnerCalculated state`() {

            val winnerResult = PlayedCard.WinnerCard(card1) to PlayedCard.LooserCard(card2)
            every { updateGameRoundResultUseCase.execute(any()) } just runs
            every { getWinnerCardUseCase.execute(card1 to card2) } returns winnerResult

            viewModel.onRound()

            verify(exactly = 1) { updateGameRoundResultUseCase.execute(winnerResult) }

            viewModel.gameState.value shouldBe GameState.RoundWinnerCalculated(true)
        }

        @Test
        fun `GIVEN round for player two WHEN onRound THEN RoundWinnerCalculated state`() {

            val winnerResult = PlayedCard.LooserCard(card1) to PlayedCard.WinnerCard(card2)
            every { updateGameRoundResultUseCase.execute(any()) } just runs
            every { getWinnerCardUseCase.execute(card1 to card2) } returns winnerResult

            viewModel.onRound()

            verify(exactly = 1) { updateGameRoundResultUseCase.execute(winnerResult) }

            viewModel.gameState.value shouldBe GameState.RoundWinnerCalculated(false)
        }
    }

    @Test
    fun `WHEN onWinnerCardDisplayed THEN RoundFinished state`() {
        val card1 = mockk<PlayerCard>()
        val card2 = mockk<PlayerCard>()
        val card3 = mockk<PlayerCard>()
        val card4 = mockk<PlayerCard>()
        val playerOneScore = 6
        val playerTwoScore = 8
        val playerOne = Player.PlayerOne(listOf(card1, card3))
        val playerTwo = Player.PlayerTwo(listOf(card2, card4))
        val players = playerOne to playerTwo
        val expectedPlayerOne = Player.PlayerOne(listOf(card1))
        val expectedPlayerTwo = Player.PlayerTwo(listOf(card2))

        every { updateGameStateUseCase.execute(any()) } just runs
        every { getGameStateUseCase.execute() } returns players
        every { getRoundSummaryPoints.execute() } returns (playerOneScore to playerTwoScore)

        viewModel.onWinnerCardDisplayed()

        viewModel.gameState.value shouldBe GameState.RoundFinished(
            expectedPlayerOne,
            playerOneScore,
            expectedPlayerTwo,
            playerTwoScore
        )

        verify(exactly = 1) {
            updateGameStateUseCase.execute(
                expectedPlayerOne to expectedPlayerTwo
            )
        }
    }

    @Nested
    inner class OnNewRoundStart {

        @Test
        fun `GIVEN game no ended WHEN onNewRoundStart THEN do not update new state`() {
            every { hasGameEndUseCase.execute() } returns false

            viewModel.onNewRoundStart()

            viewModel.gameState.value shouldBe null
        }

        @Test
        fun `GIVEN game ended WHEN onNewRoundStart THEN GameFinished state`() {
            val summary = mockk<GameSummary>()
            every { hasGameEndUseCase.execute() } returns true
            every { getGameSummaryUseCase.execute() } returns summary

            viewModel.onNewRoundStart()

            viewModel.gameState.value shouldBe GameState.GameFinished(summary)
        }
    }

    @Test
    fun `WHEN onReset THEN reset state`() {
        every { resetGameStateUseCase.execute() } just runs

        viewModel.onReset()

        viewModel.gameState.value shouldBe GameState.GameRest
    }
}
