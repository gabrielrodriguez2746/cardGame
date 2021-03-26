package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.listEqual
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SuitsPriorityRepositoryTest {

    private lateinit var repository: SuitsPriorityRepository

    @BeforeEach
    fun setup() {
        repository = SuitsPriorityRepository()
    }

    @Test
    fun `WHEN getSuitsPriority THEN result has different order from enum values`() {
        Assertions.assertFalse {
            repository.getSuitsPriority() listEqual CardSuit.values().toList()
        }
    }

    @Test
    fun `WHEN clean THEN get new priority order`() {
        val suitsPriority = repository.getSuitsPriority()

        repository.clean()
        Assertions.assertFalse {
            repository.getSuitsPriority() listEqual suitsPriority
        }
    }
}
