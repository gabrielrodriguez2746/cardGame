package com.codechallenge.game.di

import com.codechallenge.game.data.repositories.CardsGameRepository
import com.codechallenge.game.data.repositories.GameRepository
import com.codechallenge.game.data.repositories.GameRoundRepository
import com.codechallenge.game.data.repositories.GameStateRepository
import com.codechallenge.game.data.repositories.PriorityRepository
import com.codechallenge.game.data.repositories.RoundRepository
import com.codechallenge.game.data.repositories.StateRepository
import com.codechallenge.game.data.repositories.SuitsPriorityRepository
import com.codechallenge.game.presentation.CardsGameViewModel
import com.codechallenge.game.presentation.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
abstract class GameFragmentModule {

    @Binds
    abstract fun bindCardsGameRepository(repository: CardsGameRepository): GameRepository

    @Binds
    abstract fun bindGameRoundRepository(repository: GameRoundRepository): RoundRepository

    @Binds
    abstract fun bindGameStateRepository(repository: GameStateRepository): StateRepository

    @Binds
    abstract fun bindSuitsPriorityRepository(repository: SuitsPriorityRepository): PriorityRepository

    @Binds
    abstract fun bingCardsGameViewModel(viewModel: CardsGameViewModel): GameViewModel

    companion object {

        @Provides
        fun provideCoroutineDispatcher(): CoroutineDispatcher {
            return Dispatchers.Default
        }
    }
}
