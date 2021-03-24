package com.codechallenge.cardgame.di.navigation

import com.codechallenge.cardgame.navigation.HomeNavigationHandler
import com.codechallenge.cardgame.navigation.LandingNavigationHandler
import com.codechallenge.cardgame.navigation.RulesNavigationHandler
import com.codechallenge.navigation.NavigatorHandler
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
internal abstract class NavigationSetModule {

    @Binds
    @IntoSet
    abstract fun bindLandingNavigationHandler(navigator: LandingNavigationHandler): NavigatorHandler

    @Binds
    @IntoSet
    abstract fun bindRulesNavigationHandler(navigator: RulesNavigationHandler): NavigatorHandler

    @Binds
    @IntoSet
    abstract fun bindHomeNavigationHandler(navigator: HomeNavigationHandler): NavigatorHandler
}
