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
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
@ExperimentalCoroutinesApi
internal class CardsGameViewModelTest {

    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

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

    private lateinit var viewModel: CardsGameViewModel

    @BeforeEach
    fun setUp() {
        viewModel = CardsGameViewModel(
            getPlayersCardUseCase,
            getSuitsPriorityUseCase,
            updateGameStateUseCase,
            getGameStateUseCase,
            getWinnerCardUseCase,
            updateGameRoundResultUseCase,
            getRoundSummaryPoints,
            resetGameStateUseCase,
            hasGameEndUseCase,
            getGameSummaryUseCase,
            testCoroutineDispatcher
        )
    }

    @AfterEach
    fun setup() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `WHEN onStart THEN GameStarted state`() {
        val playerOne = mockk<Player>()
        val playerTwo = mockk<Player>()
        val players = playerOne to playerTwo
        val priorities = listOf<PlayerCardSuit>()
        coEvery { updateGameStateUseCase.execute(any()) } just runs
        coEvery { getPlayersCardUseCase.execute() } returns players
        coEvery { getSuitsPriorityUseCase.execute() } returns priorities

        viewModel.onStart()

        viewModel.gameState.value shouldBe GameState.GameStarted(
            playerOne,
            playerTwo,
            priorities
        )
        coVerify(exactly = 1) { updateGameStateUseCase.execute(players) }
    }

    @Test
    fun `WHEN onHit THEN RoundPlayed state`() {
        val card1 = mockk<PlayerCard>()
        val card2 = mockk<PlayerCard>()
        val playerOne = Player.PlayerOne(listOf(card1))
        val playerTwo = Player.PlayerTwo(listOf(card2))
        val players = playerOne to playerTwo
        coEvery { getGameStateUseCase.execute() } returns players

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
            coEvery { getGameStateUseCase.execute() } returns players
        }

        @Test
        fun `GIVEN round for player one WHEN onRound THEN RoundWinnerCalculated state`() {

            val winnerResult = PlayedCard.WinnerCard(card1) to PlayedCard.LooserCard(card2)
            coEvery { updateGameRoundResultUseCase.execute(any()) } just runs
            coEvery { getWinnerCardUseCase.execute(card1 to card2) } returns winnerResult

            viewModel.onRound()

            coVerify(exactly = 1) { updateGameRoundResultUseCase.execute(winnerResult) }

            viewModel.gameState.value shouldBe GameState.RoundWinnerCalculated(true)
        }

        @Test
        fun `GIVEN round for player two WHEN onRound THEN RoundWinnerCalculated state`() {

            val winnerResult = PlayedCard.LooserCard(card1) to PlayedCard.WinnerCard(card2)
            coEvery { updateGameRoundResultUseCase.execute(any()) } just runs
            coEvery { getWinnerCardUseCase.execute(card1 to card2) } returns winnerResult

            viewModel.onRound()

            coVerify(exactly = 1) { updateGameRoundResultUseCase.execute(winnerResult) }

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

        coEvery { updateGameStateUseCase.execute(any()) } just runs
        coEvery { getGameStateUseCase.execute() } returns players
        coEvery { getRoundSummaryPoints.execute() } returns (playerOneScore to playerTwoScore)

        viewModel.onWinnerCardDisplayed()

        viewModel.gameState.value shouldBe GameState.RoundFinished(
            expectedPlayerOne,
            playerOneScore,
            expectedPlayerTwo,
            playerTwoScore
        )

        coVerify(exactly = 1) {
            updateGameStateUseCase.execute(
                expectedPlayerOne to expectedPlayerTwo
            )
        }
    }

    @Nested
    inner class OnNewRoundStart {

        @Test
        fun `GIVEN game no ended WHEN onNewRoundStart THEN do not update new state`() {
            coEvery { hasGameEndUseCase.execute() } returns false

            viewModel.onNewRoundStart()

            viewModel.gameState.value shouldBe null
        }

        @Test
        fun `GIVEN game ended WHEN onNewRoundStart THEN GameFinished state`() {
            val summary = mockk<GameSummary>()
            coEvery { hasGameEndUseCase.execute() } returns true
            coEvery { getGameSummaryUseCase.execute() } returns summary

            viewModel.onNewRoundStart()

            viewModel.gameState.value shouldBe GameState.GameFinished(summary)
        }
    }

    @Test
    fun `WHEN onReset THEN reset state`() {
        coEvery { resetGameStateUseCase.execute() } just runs

        viewModel.onReset()

        viewModel.gameState.value shouldBe GameState.GameRest
    }
}
