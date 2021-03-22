package com.codechallenge.cardgame

import android.app.Application
import com.codechallenge.cardgame.di.DaggerApplicationComponent
import com.codechallenge.navigation.NavigationProvider
import com.codechallenge.navigation.NavigatorHandler
import javax.inject.Inject

class CardGameApplication : Application(), NavigationProvider {

    @Inject
    lateinit var navigatior: NavigatorHandler

    override fun onCreate() {
        super.onCreate()
        with(DaggerApplicationComponent.factory().create(this)) {
            inject(this@CardGameApplication)
        }
    }

    override fun getNavigator(): NavigatorHandler = navigatior
}
