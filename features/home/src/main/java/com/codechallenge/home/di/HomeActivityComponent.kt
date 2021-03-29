package com.codechallenge.home.di

import androidx.fragment.app.Fragment
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.injector.DependedNodeComponentFactory
import com.codechallenge.injector.IndependentNodeComponentFactory
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.NodeComponentDependencies
import com.codechallenge.injector.PerActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [HomeActivityModule::class])
@PerActivity
interface HomeActivityComponent : NodeComponent {

    interface Dependencies : NodeComponentDependencies, RulesNavigationDependencies, GameNavigationDependencies

    interface RulesNavigationDependencies {
        val rulesIdentifier: Int
        fun <T : Fragment> rulesFragmentClass(): Class<T>
        val rulesFragmentName: String
    }

    interface GameNavigationDependencies {
        fun <T : Fragment> gameFragmentClass(): Class<T>
        val gameFragmentName: String
        val componentFactory: IndependentNodeComponentFactory<NodeComponent>
    }

    @Component.Factory
    interface Factory : DependedNodeComponentFactory<HomeActivityComponent, Dependencies> {
        override fun create(@BindsInstance dependencies: Dependencies): HomeActivityComponent
    }

    fun inject(view: HomeActivity)
}
