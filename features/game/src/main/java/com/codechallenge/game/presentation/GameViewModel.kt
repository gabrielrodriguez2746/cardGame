package com.codechallenge.game.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val getPlayersCardUseCase: GetPlayersCardUseCase,
    private val getSuitsPriorityUseCase: GetSuitsPriorityUseCase,
    private val updateGameStateUseCase: UpdateGameStateUseCase,
    private val getGameStateUseCase: GetGameStateUseCase,
    private val getWinnerCardUseCase: GetWinnerCardUseCase,
    private val updateGameRoundResultUseCase: UpdateGameRoundResultUseCase,
    private val getRoundSummaryPoints: GetRoundSummaryPoints,
    private val resetGameStateUseCase: ResetGameStateUseCase,
    private val hasGameEndUseCase: HasGameEndUseCase,
    private val getGameSummaryUseCase: GetGameSummaryUseCase
) : ViewModel() {

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    fun onStart() {
        val initialCardsDraw = getPlayersCardUseCase.execute()
        val (playerOne, playerTwo) = initialCardsDraw
        updateGameStateUseCase.execute(initialCardsDraw)
        _gameState.postValue(
            GameState.GameStarted(
                playerOne,
                playerTwo,
                getSuitsPriorityUseCase.execute()
            )
        )
    }

    fun onHit() {
        val (playerOne, playerTwo) = getGameStateUseCase.execute()
        _gameState.postValue(GameState.RoundPlayed(playerOne.cardDeck.last(), playerTwo.cardDeck.last()))
    }

    fun onRound() {
        val (playerOne, playerTwo) = getGameStateUseCase.execute()
        val playedCards = getWinnerCardUseCase.execute(playerOne.cardDeck.last() to playerTwo.cardDeck.last())
        val (playedCard1, _) = playedCards
        updateGameRoundResultUseCase.execute(playedCards)
        _gameState.postValue(GameState.RoundWinnerCalculated(playedCard1 is PlayedCard.WinnerCard))
    }

    fun onWinnerCardDisplayed() {
        val (playerOne, playerTwo) = getGameStateUseCase.execute()
        val playerOneNewDesk = playerOne.cardDeck.toMutableList().apply { removeLast() }
        val playerTwoNewDesk = playerTwo.cardDeck.toMutableList().apply { removeLast() }

        val playersAfterRound = Player.PlayerOne(playerOneNewDesk) to Player.PlayerTwo(playerTwoNewDesk)
        updateGameStateUseCase.execute(playersAfterRound)
        val (player1AfterRound, player2AfterRound) = playersAfterRound
        val (player1Score, playerTwoScore) = getRoundSummaryPoints.execute()
        _gameState.postValue(
            GameState.RoundFinished(
                player1AfterRound,
                player1Score,
                player2AfterRound,
                playerTwoScore
            )
        )
    }

    fun onNewRoundStart() {
        if (hasGameEndUseCase.execute()) {
            _gameState.postValue(
                GameState.GameFinished(getGameSummaryUseCase.execute())
            )
        }
    }

    fun onReset() {
        resetGameStateUseCase.execute()
        _gameState.postValue(GameState.GameRest)
    }

    sealed class GameState {
        object GameRest : GameState()

        data class GameStarted(
            val playerOne: Player,
            val playerTwo: Player,
            val suits: List<PlayerCardSuit>
        ) : GameState()

        data class RoundPlayed(val card1: PlayerCard, val card2: PlayerCard) : GameState()

        data class RoundWinnerCalculated(val hasPlayerOneWon: Boolean) : GameState()

        data class RoundFinished(
            val player1: Player,
            val playerOnePoints: Int,
            val player2: Player,
            val playerTwoPoints: Int
        ) : GameState()

        data class GameFinished(val summary: GameSummary) : GameState()
    }
}
