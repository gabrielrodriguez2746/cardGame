package com.codechallenge.game.presentation

import androidx.lifecycle.LiveData

interface GameViewModel {

    val gameState: LiveData<GameState>

    fun onStart()
    fun onHit()
    fun onRound()
    fun onWinnerCardDisplayed()
    fun onNewRoundStart()
    fun onReset()
}
