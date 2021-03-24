package com.codechallenge.game.data.factory

import com.codechallenge.game.data.model.CardSuit
import com.codechallenge.game.data.model.CardValue
import com.codechallenge.game.data.model.PokerCard
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CardSetFactoryTest {

    private lateinit var factory: CardSetFactory

    @BeforeEach
    fun setup() {
        factory = CardSetFactory()
    }

    @Test
    fun `GIVEN all possible suits WHEN get THEN return expected`() {
        val cardValues = CardValue.values()
        CardSuit.values().forEach { suit ->
            with(factory.get(suit)) {
                Assertions.assertTrue { this.size == 13 }
                assertForEach { it.suits == suit && cardValues.contains(it.value) }
            }
        }
    }

    private fun List<PokerCard>.assertForEach(block: (card: PokerCard) -> Boolean) {
        forEach { card ->
            Assertions.assertTrue { block.invoke(card) }
        }
    }
}
