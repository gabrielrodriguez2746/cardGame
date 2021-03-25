package com.codechallenge.game.domain.model

import androidx.annotation.DrawableRes
import com.codechallenge.game.R

sealed class PlayerCardSuit(@DrawableRes val resource: Int) {
    object Club : PlayerCardSuit(R.drawable.ic_club)
    object Diamond : PlayerCardSuit(R.drawable.ic_diamond)
    object Hearts : PlayerCardSuit(R.drawable.ic_heart)
    object Spades : PlayerCardSuit(R.drawable.ic_spades)
}
