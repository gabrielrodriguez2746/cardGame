package com.codechallenge.cardgame.di.navigation

import com.codechallenge.navigation.NavigatorHandler
import dagger.Module
import dagger.Provides

@Module(includes = [NavigationSetModule::class])
object NavigationModule {

    @Provides
    fun provideNavigatorHandler(
        navigators: Set<@JvmSuppressWildcards NavigatorHandler>
    ): NavigatorHandler {
        return navigators.reduce { handlerResult, nextHandler ->
            handlerResult.setNext(nextHandler)
        }
    }
}
