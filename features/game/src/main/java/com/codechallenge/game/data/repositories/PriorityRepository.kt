package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit

interface PriorityRepository {
    suspend fun getSuitsPriority(): List<CardSuit>
    suspend fun clean()
}
