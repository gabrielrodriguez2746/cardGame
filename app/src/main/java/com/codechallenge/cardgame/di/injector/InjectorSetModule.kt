package com.codechallenge.cardgame.di.injector

import com.codechallenge.cardgame.injector.HomeActivityInjectionHandler
import com.codechallenge.injector.InjectionHandler
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
internal abstract class InjectorSetModule {

    @Binds
    @IntoSet
    abstract fun bindHomeActivityInjectionHandler(handler: HomeActivityInjectionHandler): InjectionHandler
}
