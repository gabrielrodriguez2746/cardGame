package com.codechallenge.game.domain.formatter

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.domain.model.PlayedCard
import com.codechallenge.game.domain.model.PlayerCard
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class PlayedCardFormatterTest {

    @Test
    fun `WHEN toPokerCard THEN call playerCardFormatter and return expected`() {
        val playerCard = mockk<PlayerCard>()
        val expected = mockk<PokerCard>()
        val input = mockk<PlayedCard> {
            every { card } returns playerCard
        }
        val playerCardFormatter = mockk<PlayerCardFormatter> {
            every { with(this@mockk) { playerCard.toPokerCard() } } returns expected
        }

        with(PlayedCardFormatter(playerCardFormatter)) {
            input.toPokerCard() shouldBe expected
        }
    }
}
