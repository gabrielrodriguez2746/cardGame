package com.codechallenge.game.data.repositories

import com.codechallenge.game.data.factory.CardSetFactory
import com.codechallenge.game.data.model.CardSuit
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CardsGameRepositoryTest {

    private val cardSetFactory: CardSetFactory = mockk()

    private lateinit var repository: CardsGameRepository

    @BeforeEach
    fun setUp() {
        repository = CardsGameRepository(cardSetFactory)
    }

    @Test
    fun `WHEN getSetOfCard THEN get all suits combinations`() {
        every { cardSetFactory.get(any()) } returns emptyList()

        repository.getSetOfCard()

        verify {
            cardSetFactory.get(CardSuit.DIAMOND)
            cardSetFactory.get(CardSuit.SPADES)
            cardSetFactory.get(CardSuit.HEARTS)
            cardSetFactory.get(CardSuit.CLUB)
        }
    }
}
