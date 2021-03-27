package com.codechallenge.home.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.codechallenge.home.R
import com.codechallenge.home.databinding.ActivityHomeBinding
import com.codechallenge.home.di.HomeActivityComponent
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionNode
import com.codechallenge.injector.InjectionProvider
import com.codechallenge.injector.getParentInjectionHandler
import com.codechallenge.injector.plug
import com.codechallenge.injector.unplug
import com.codechallenge.navigation.NavigationProvider
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.navigation.getParentNavigator
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), NavigationProvider, InjectionNode<HomeActivityComponent>, InjectionProvider {

    @Inject
    lateinit var navigatorHandler: NavigatorHandler

    private val _navigatorHandler by lazy {
        navigatorHandler.setNext(getParentNavigator())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plug(this)
        with(ActivityHomeBinding.inflate(layoutInflater)) {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragment_container_view)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unplug(this)
    }

    override fun getNavigator(): NavigatorHandler {
        return _navigatorHandler
    }

    override fun getInjectionHandler(): InjectionHandler {
        return getParentInjectionHandler()
    }

    override fun inject(component: HomeActivityComponent) {
        component.inject(this)
    }
}
