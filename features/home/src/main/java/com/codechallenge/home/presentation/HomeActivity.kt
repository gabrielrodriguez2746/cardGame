package com.codechallenge.home.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.codechallenge.home.R
import com.codechallenge.home.databinding.ActivityHomeBinding
import com.codechallenge.home.di.HomeActivityComponent
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionProvider
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.SaveStateInjectionNode
import com.codechallenge.injector.plug
import com.codechallenge.injector.toTypedComponent
import com.codechallenge.injector.unplug
import com.codechallenge.navigation.NavigationProvider
import com.codechallenge.navigation.NavigatorHandler
import javax.inject.Inject

class HomeActivity :
    AppCompatActivity(),
    NavigationProvider,
    SaveStateInjectionNode<HomeActivityComponent>,
    InjectionProvider {

    override val identifier: String get() = HomeActivity::class.java.simpleName
    override var saveState: Boolean = false

    @Inject
    lateinit var injectorHandler: InjectionHandler

    @Inject
    lateinit var navigatorHandler: NavigatorHandler

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState = true
    }

    override fun onDestroy() {
        super.onDestroy()
        unplug(this)
        saveState = false
    }

    override fun getNavigator(): NavigatorHandler {
        return navigatorHandler
    }

    override fun getInjectionHandler(): InjectionHandler {
        return injectorHandler
    }

    override fun inject(component: NodeComponent) {
        component.toTypedComponent<HomeActivityComponent>().inject(this)
    }
}
