package com.codechallenge.home.di

import com.codechallenge.home.di.HomeActivityComponent.Dependencies
import com.codechallenge.home.di.HomeActivityComponent.GameNavigationDependencies
import com.codechallenge.home.di.HomeActivityComponent.RulesNavigationDependencies
import com.codechallenge.home.navigation.HomeNavigationHandler
import com.codechallenge.navigation.NavigatorHandler
import dagger.Binds
import dagger.Module

@Module
abstract class HomeActivityModule {

    @Binds
    abstract fun bindHomeNavigationHandler(navigator: HomeNavigationHandler): NavigatorHandler

    @Binds
    abstract fun bindRulesNavigationDependencies(dependencies: Dependencies): RulesNavigationDependencies

    @Binds
    abstract fun bindGameNavigationDependencies(dependencies: Dependencies): GameNavigationDependencies
}
