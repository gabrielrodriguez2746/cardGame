package com.codechallenge.game.domain.helpers

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.listEqual
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CardShufflerTest {

    @Test
    fun `GIVEN input list WHEN shuffleDeck to not get same list`() {
        val input = listOf<PokerCard>(mockk(), mockk(), mockk(), mockk(), mockk())
        with(CardShuffler()) {
            Assertions.assertFalse {
                input.shuffleDeck() listEqual input
            }
        }
    }
}
