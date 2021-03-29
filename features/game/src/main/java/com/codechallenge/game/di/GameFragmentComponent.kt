package com.codechallenge.game.di

import com.codechallenge.game.presentation.GameFragment
import com.codechallenge.injector.IndependentNodeComponentFactory
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.PerFragment
import dagger.Component

@Component
@PerFragment
interface GameFragmentComponent : NodeComponent {
    @Component.Factory
    interface Factory : IndependentNodeComponentFactory<GameFragmentComponent> {
        override fun create(): GameFragmentComponent
    }

    fun inject(view: GameFragment)
}
