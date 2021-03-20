package com.codechallenge.cardgame.di

import android.app.Application
import com.codechallenge.cardgame.CardGameApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NavigationModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }

    fun inject(application: CardGameApplication)

}