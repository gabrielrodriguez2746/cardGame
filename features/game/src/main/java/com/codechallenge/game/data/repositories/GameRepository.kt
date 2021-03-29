package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.PokerCard

interface GameRepository {

    suspend fun getSetOfCard(): List<PokerCard>
}
