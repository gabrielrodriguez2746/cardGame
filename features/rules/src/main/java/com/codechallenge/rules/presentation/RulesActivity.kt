package com.codechallenge.rules.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.codechallenge.navigation.NavigationProvider
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.navigation.getParentNavigator
import com.codechallenge.rules.R
import com.codechallenge.rules.databinding.ActivityRulesBinding

class RulesActivity : AppCompatActivity(), NavigationProvider {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(ActivityRulesBinding.inflate(layoutInflater)) {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RulesFragment>(R.id.fragment_container_view)
            }
        }
    }

    override fun getNavigator(): NavigatorHandler {
        return getParentNavigator()
    }
}
