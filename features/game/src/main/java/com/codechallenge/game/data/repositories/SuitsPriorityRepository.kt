package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import javax.inject.Inject

class SuitsPriorityRepository @Inject constructor() {

    private var suitsPriorityList = CardSuit.values().toMutableList().apply { shuffle() }

    fun getSuitsPriority(): List<CardSuit> = suitsPriorityList

    fun clean() {
        suitsPriorityList = CardSuit.values().toMutableList().apply { shuffle() }
    }
}
