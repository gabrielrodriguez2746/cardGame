package com.codechallenge.home.di

import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.PerActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [HomeActivityModule::class])
@PerActivity
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
