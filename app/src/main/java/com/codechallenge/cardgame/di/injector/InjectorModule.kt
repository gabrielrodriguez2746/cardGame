package com.codechallenge.cardgame.di.injector

import com.codechallenge.injector.InjectionHandler
import dagger.Module
import dagger.Provides

@Module(includes = [InjectorSetModule::class])
object InjectorModule {

    @Provides
    fun provideInjectionHandler(
        navigators: Set<@JvmSuppressWildcards InjectionHandler>
    ): InjectionHandler {
        return navigators.reduce { handlerResult, nextHandler ->
            handlerResult.setNext(nextHandler)
        }
    }
}
