package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import javax.inject.Inject

class SuitsPriorityRepository @Inject constructor() {

    private val suitsPriorityList = CardSuit.values().toMutableList().apply { shuffle() }

    fun getSuitsPriority(): List<CardSuit> = suitsPriorityList
}
