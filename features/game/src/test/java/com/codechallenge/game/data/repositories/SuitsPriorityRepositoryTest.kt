package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.listEqual
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SuitsPriorityRepositoryTest {

    private lateinit var repository: SuitsPriorityRepository

    @BeforeEach
    fun setup() {
        repository = SuitsPriorityRepository()
    }

    @Test
    fun `WHEN getSuitsPriority THEN result has different order from enum values`() = runBlocking {

        (repository.getSuitsPriority() listEqual CardSuit.values().toList()) shouldBe false
    }

    @Test
    fun `WHEN clean THEN get new priority order`() = runBlocking {
        val suitsPriority = repository.getSuitsPriority()

        repository.clean()

        (repository.getSuitsPriority() listEqual suitsPriority) shouldBe false
    }
}
