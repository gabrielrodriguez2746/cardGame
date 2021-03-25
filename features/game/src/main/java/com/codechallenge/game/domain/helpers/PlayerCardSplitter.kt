package com.codechallenge.game.domain.helpers

import com.codechallenge.game.domain.model.PlayerCard
import javax.inject.Inject

class PlayerCardSplitter @Inject constructor() {

    private val initialPair get() = mutableListOf<PlayerCard>() to mutableListOf<PlayerCard>()

    fun List<PlayerCard>.split(): Pair<List<PlayerCard>, List<PlayerCard>> {
        return foldIndexed(initialPair) { index, result, card ->
            if (index.isOdd()) {
                result.first.add(card)
            } else {
                result.second.add(card)
            }
            result
        }
    }

    private fun Int.isOdd() = this % 2 == 0
}
