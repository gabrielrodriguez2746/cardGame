package com.codechallenge.cardgame.injector

import com.codechallenge.game.di.DaggerGameFragmentComponent
import com.codechallenge.game.di.GameFragmentComponent
import com.codechallenge.game.presentation.GameFragment
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionNode
import com.codechallenge.injector.NodeComponent
import javax.inject.Inject

class GameFragmentInjectionHandler @Inject constructor() : InjectionHandler() {

    private var fragmentComponent: GameFragmentComponent? = null

    override fun <T : NodeComponent> plug(subject: InjectionNode<T>) {
        if (subject is GameFragment) {
            withComponent { subject.inject(it) }
        } else {
            plugNext(subject)
        }
    }

    override fun <T : NodeComponent> unplug(subject: InjectionNode<T>) {
        if (subject is GameFragment && !subject.saveState) {
            releaseComponentMemory()
        } else {
            unPlugNext(subject)
        }
    }

    internal fun releaseComponentMemory() {
        fragmentComponent = null
    }

    internal fun withComponent(block: (component: GameFragmentComponent) -> Unit) {
        fragmentComponent?.let {
            block.invoke(it)
        } ?: run {
            fragmentComponent = DaggerGameFragmentComponent.factory().create()
                .also { block.invoke(it) }
        }
    }
}
