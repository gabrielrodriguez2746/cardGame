package com.codechallenge.cardgame

import android.app.Application
import com.codechallenge.cardgame.di.DaggerApplicationComponent
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionProvider
import com.codechallenge.navigation.NavigationProvider
import com.codechallenge.navigation.NavigatorHandler
import javax.inject.Inject

class CardGameApplication : Application(), NavigationProvider, InjectionProvider {

    @Inject
    lateinit var navigatorHandler: NavigatorHandler

    @Inject
    lateinit var injectorHandler: InjectionHandler

    override fun onCreate() {
        super.onCreate()
        with(DaggerApplicationComponent.factory().create(this)) {
            inject(this@CardGameApplication)
        }
    }

    override fun getNavigator(): NavigatorHandler = navigatorHandler
    override fun getInjectionHandler(): InjectionHandler = injectorHandler
}
