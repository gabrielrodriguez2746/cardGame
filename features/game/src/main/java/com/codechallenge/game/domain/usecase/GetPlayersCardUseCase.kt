package com.codechallenge.game.domain.usecase

import com.codechallenge.game.data.model.PokerCard
import com.codechallenge.game.data.repositories.CardsGameRepository
import com.codechallenge.game.domain.formatter.PlayerCardFormatter
import com.codechallenge.game.domain.helpers.CardShuffler
import com.codechallenge.game.domain.helpers.PlayerCardSplitter
import com.codechallenge.game.domain.model.Player
import com.codechallenge.game.domain.model.PlayerCard
import javax.inject.Inject

class GetPlayersCardUseCase @Inject constructor(
    private val cardsGameRepository: CardsGameRepository,
    private val playerCardFormatter: PlayerCardFormatter,
    private val playerCardSplitter: PlayerCardSplitter,
    private val cardShuffler: CardShuffler
) {

    fun execute(): Pair<Player, Player> {
        val (firstPlayerCards, secondPlayerCards) =
            with(playerCardSplitter) { cardsGameRepository.getSetOfCard().toPlayerCards().split() }
        return Player.PlayerOne(firstPlayerCards) to Player.PlayerTwo(secondPlayerCards)
    }

    private fun List<PokerCard>.toPlayerCards(): List<PlayerCard> {
        return with(cardShuffler) { this@toPlayerCards.shuffleDeck() }
            .map { with(playerCardFormatter) { it.toPlayerCard() } }
    }
}
