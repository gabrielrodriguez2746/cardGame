package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SuitsPriorityRepositoryTest {

    @Test
    fun `WHEN getSuitsPriority THEN result has different order from enum values`() {
        val repository = SuitsPriorityRepository()
        Assertions.assertFalse {
            repository.getSuitsPriority().listEqualToSuitsValues()
        }
    }

    private fun List<CardSuit>.listEqualToSuitsValues(): Boolean {
        val defaultValues = CardSuit.values()
        return when {
            this.size != defaultValues.size -> false
            else -> {
                val pairList = zip(defaultValues)
                return pairList.all { (suit1, suit2) -> suit1 == suit2 }
            }
        }
    }
}
