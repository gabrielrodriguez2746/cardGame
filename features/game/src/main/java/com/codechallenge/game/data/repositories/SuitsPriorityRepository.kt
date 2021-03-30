package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.injector.PerFragment
import javax.inject.Inject

@PerFragment
class SuitsPriorityRepository @Inject constructor() : PriorityRepository {

    private var suitsPriorityList = CardSuit.values().toMutableList().apply { shuffle() }

    override suspend fun getSuitsPriority(): List<CardSuit> = suitsPriorityList

    override suspend fun clean() {
        suitsPriorityList = CardSuit.values().toMutableList().apply { shuffle() }
    }
}
