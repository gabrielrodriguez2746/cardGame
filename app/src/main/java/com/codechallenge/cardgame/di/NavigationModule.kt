package com.codechallenge.cardgame.di

import com.codechallenge.cardgame.navigation.LandingNavigationHandler
import com.codechallenge.navigation.NavigatorHandler
import dagger.Module
import dagger.Provides

@Module
object NavigationModule {

    @Provides
    fun provideNavigatorHandler(
        landingNavigator: LandingNavigationHandler
    ): NavigatorHandler {
        return landingNavigator
    }

}

