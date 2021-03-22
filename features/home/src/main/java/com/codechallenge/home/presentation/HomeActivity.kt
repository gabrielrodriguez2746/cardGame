package com.codechallenge.home.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.codechallenge.home.R
import com.codechallenge.home.databinding.ActivityHomeBinding
import com.codechallenge.navigation.NavigationProvider
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.navigation.getParentNavigator

class HomeActivity : AppCompatActivity(), NavigationProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun getNavigator(): NavigatorHandler {
        return getParentNavigator()
    }
}
