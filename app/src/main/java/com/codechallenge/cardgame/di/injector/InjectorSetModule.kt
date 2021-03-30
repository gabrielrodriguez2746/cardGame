package com.codechallenge.cardgame.di.injector

import androidx.fragment.app.Fragment
import com.codechallenge.cardgame.R
import com.codechallenge.cardgame.di.HomeIdentifier
import com.codechallenge.cardgame.injector.HomeActivityInjectionHandler
import com.codechallenge.game.di.DaggerGameFragmentComponent
import com.codechallenge.game.di.GameFragmentComponent
import com.codechallenge.game.presentation.GameFragment
import com.codechallenge.home.di.DaggerHomeActivityComponent
import com.codechallenge.home.di.HomeActivityComponent
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.injector.IndependentNodeComponentFactory
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.NodeComponentDependencies
import com.codechallenge.injector.NodeComponentFactory
import com.codechallenge.rules.presentation.RulesFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
internal abstract class InjectorSetModule {

    @Binds
    @IntoSet
    abstract fun bindHomeActivityInjectionHandler(handler: HomeActivityInjectionHandler): InjectionHandler

    companion object {

        @Provides
        @HomeIdentifier
        fun provideHomeIdentifier(): String {
            return HomeActivity::class.java.simpleName
        }

        @Provides
        fun provideGameComponentFactory(): NodeComponentFactory<NodeComponent> {
            return DaggerHomeActivityComponent.factory().toTyped()
        }

        @Provides
        fun provideHomeDependencies(): NodeComponentDependencies {
            return object : HomeActivityComponent.Dependencies {
                override val rulesIdentifier: Int = R.id.buttonNext
                override fun <T : Fragment> rulesFragmentClass(): Class<T> = RulesFragment::class.java.toTyped()
                override val rulesFragmentName: String = RulesFragment::class.java.simpleName
                override fun <T : Fragment> gameFragmentClass(): Class<T> = GameFragment::class.java.toTyped()
                override val gameFragmentName: String = GameFragment::class.java.simpleName
                override val componentFactory: IndependentNodeComponentFactory<NodeComponent> =
                    DaggerGameFragmentComponent.factory().toTyped()
            }
        }

        private inline fun <reified T> Class<*>.toTyped(): T {
            return this as T
        }

        private inline fun <reified T : NodeComponentFactory<*>> GameFragmentComponent.Factory.toTyped(): T {
            return this as T
        }

        private inline fun <reified T : NodeComponentFactory<*>> HomeActivityComponent.Factory.toTyped(): T {
            return this as T
        }
    }
}
