package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.listEqual
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SuitsPriorityRepositoryTest {

    @Test
    fun `WHEN getSuitsPriority THEN result has different order from enum values`() {
        Assertions.assertFalse {
            SuitsPriorityRepository().getSuitsPriority() listEqual CardSuit.values().toList()
        }
    }
}
