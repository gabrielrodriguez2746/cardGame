package com.codechallenge.cardgame.injector

import androidx.fragment.app.Fragment
import com.codechallenge.cardgame.R
import com.codechallenge.game.presentation.GameFragment
import com.codechallenge.home.di.DaggerHomeActivityComponent
import com.codechallenge.home.di.HomeActivityComponent
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionNode
import com.codechallenge.injector.NodeComponent
import com.codechallenge.rules.presentation.RulesFragment
import javax.inject.Inject

class HomeActivityInjectionHandler @Inject constructor() : InjectionHandler() {

    private var activityComponent: HomeActivityComponent? = null

    override fun <T : NodeComponent> plug(subject: InjectionNode<T>) {
        if (subject is HomeActivity) {
            withComponent { subject.inject(it) }
        } else {
            plugNext(subject)
        }
    }

    override fun <T : NodeComponent> unplug(subject: InjectionNode<T>) {
        if (subject is HomeActivity) {
            releaseComponentMemory()
        } else {
            unPlugNext(subject)
        }
    }

    internal fun releaseComponentMemory() {
        activityComponent = null
    }

    internal fun withComponent(block: (component: HomeActivityComponent) -> Unit) {
        activityComponent?.let {
            block.invoke(it)
        } ?: run {
            activityComponent = DaggerHomeActivityComponent.factory().create(getHomeDependencies())
                .also { block.invoke(it) }
        }
    }

    private fun getHomeDependencies() = object : HomeActivityComponent.Dependencies {
        override val rulesIdentifier: Int = R.id.buttonNext
        override fun <T : Fragment> rulesFragmentClass(): Class<T> =
            RulesFragment::class.java.toTyped()

        override val rulesFragmentName: String = RulesFragment::class.java.simpleName
        override fun <T : Fragment> gameFragmentClass(): Class<T> =
            GameFragment::class.java.toTyped()

        override val gameFragmentName: String = GameFragment::class.java.simpleName
    }

    inline fun <reified T> Class<*>.toTyped(): T {
        return this as T
    }
}
