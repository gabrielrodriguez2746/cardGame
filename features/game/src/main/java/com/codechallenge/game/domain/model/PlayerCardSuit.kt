package com.codechallenge.game.domain.model

import com.codechallenge.game.presentation.views.CardGameView
import com.codechallenge.game.presentation.views.CardGameView.Suit

sealed class PlayerCardSuit(@Suit val suitId: Int) {
    object Club : PlayerCardSuit(CardGameView.CLUB)
    object Diamond : PlayerCardSuit(CardGameView.DIAMOND)
    object Hearts : PlayerCardSuit(CardGameView.HEARD)
    object Spades : PlayerCardSuit(CardGameView.SPADE)
}
