package com.codechallenge.game.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.Player
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
import com.codechallenge.injector.PerFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@PerFragment
class CardsGameViewModel @Inject constructor(
    private val getPlayersCardUseCase: GetPlayersCardUseCase,
    private val getSuitsPriorityUseCase: GetSuitsPriorityUseCase,
    private val updateGameStateUseCase: UpdateGameStateUseCase,
    private val getGameStateUseCase: GetGameStateUseCase,
    private val getWinnerCardUseCase: GetWinnerCardUseCase,
    private val updateGameRoundResultUseCase: UpdateGameRoundResultUseCase,
    private val getRoundSummaryPoints: GetRoundSummaryPoints,
    private val resetGameStateUseCase: ResetGameStateUseCase,
    private val hasGameEndUseCase: HasGameEndUseCase,
    private val getGameSummaryUseCase: GetGameSummaryUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel(), GameViewModel {

    private val _gameState = MutableLiveData<GameState>()
    override val gameState: LiveData<GameState> = _gameState

    override fun onStart() {
        viewModelScope.launch(coroutineDispatcher) {
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
    }

    override fun onHit() {
        viewModelScope.launch(coroutineDispatcher) {
            val (playerOne, playerTwo) = getGameStateUseCase.execute()
            _gameState.postValue(
                GameState.RoundPlayed(
                    playerOne.cardDeck.last(),
                    playerTwo.cardDeck.last()
                )
            )
        }
    }

    override fun onRound() {
        viewModelScope.launch(coroutineDispatcher) {
            val (playerOne, playerTwo) = getGameStateUseCase.execute()
            val playedCards =
                getWinnerCardUseCase.execute(playerOne.cardDeck.last() to playerTwo.cardDeck.last())
            val (playedCard1, _) = playedCards
            updateGameRoundResultUseCase.execute(playedCards)
            _gameState.postValue(GameState.RoundWinnerCalculated(playedCard1 is PlayedCard.WinnerCard))
        }
    }

    override fun onWinnerCardDisplayed() {
        viewModelScope.launch(coroutineDispatcher) {
            val (playerOne, playerTwo) = getGameStateUseCase.execute()
            val playerOneNewDesk = playerOne.cardDeck.toMutableList().apply { removeLast() }
            val playerTwoNewDesk = playerTwo.cardDeck.toMutableList().apply { removeLast() }

            val playersAfterRound =
                Player.PlayerOne(playerOneNewDesk) to Player.PlayerTwo(playerTwoNewDesk)
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
    }

    override fun onNewRoundStart() {
        viewModelScope.launch(coroutineDispatcher) {
            if (hasGameEndUseCase.execute()) {
                _gameState.postValue(
                    GameState.GameFinished(getGameSummaryUseCase.execute())
                )
            }
        }
    }

    override fun onReset() {
        viewModelScope.launch(coroutineDispatcher) {
            resetGameStateUseCase.execute()
            _gameState.postValue(GameState.GameRest)
        }
    }
}
