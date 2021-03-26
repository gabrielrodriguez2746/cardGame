package com.codechallenge.game.data.model

sealed class PlayersGameState(open val list: List<PokerCard>) {
    data class PlayerOneState(override val list: List<PokerCard>) : PlayersGameState(list)
    data class PlayerTwoState(override val list: List<PokerCard>) : PlayersGameState(list)
}
