package com.codechallenge.home.di

import com.codechallenge.home.GameIdentifier
import com.codechallenge.home.di.HomeActivityComponent.Dependencies
import com.codechallenge.home.di.HomeActivityComponent.GameNavigationDependencies
import com.codechallenge.home.di.HomeActivityComponent.RulesNavigationDependencies
import com.codechallenge.home.injector.GameFragmentInjectionHandler
import com.codechallenge.home.navigation.HomeNavigationHandler
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.NodeComponentFactory
import com.codechallenge.navigation.NavigatorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class HomeActivityModule {

    @Binds
    abstract fun bindHomeNavigationHandler(navigator: HomeNavigationHandler): NavigatorHandler

    @Binds
    abstract fun bindGameFragmentInjectionHandler(injector: GameFragmentInjectionHandler): InjectionHandler

    @Binds
    abstract fun bindRulesNavigationDependencies(dependencies: Dependencies): RulesNavigationDependencies

    @Binds
    abstract fun bindGameNavigationDependencies(dependencies: Dependencies): GameNavigationDependencies

    companion object {

        @Provides
        @GameIdentifier
        fun provideGameIdentifier(dependencies: GameNavigationDependencies): String {
            return dependencies.gameFragmentName
        }

        @Provides
        fun provideComponentFactory(dependencies: GameNavigationDependencies): NodeComponentFactory<NodeComponent> {
            return dependencies.componentFactory
        }
    }
}
