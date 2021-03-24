package com.codechallenge.home.di

import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.injector.NodeComponent
import dagger.BindsInstance
import dagger.Component

@Component(modules = [HomeActivityModule::class])
interface HomeActivityComponent : NodeComponent {

    interface Dependencies {
        val rulesIdentifier: Int
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance dependency: Dependencies): HomeActivityComponent
    }

    fun inject(view: HomeActivity)
}
