package com.codechallenge.home.di

import com.codechallenge.home.di.HomeActivityComponent.Dependencies
import com.codechallenge.home.navigation.HomeNavigationHandler
import com.codechallenge.navigation.NavigatorHandler
import dagger.Module
import dagger.Provides

@Module
object HomeActivityModule {

    @Provides
    fun bindHomeNavigationHandler(dependencies: Dependencies): NavigatorHandler {
        return HomeNavigationHandler(dependencies.rulesIdentifier)
    }
}
