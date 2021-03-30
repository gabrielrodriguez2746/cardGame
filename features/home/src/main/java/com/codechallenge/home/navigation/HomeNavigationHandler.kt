package com.codechallenge.home.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.codechallenge.home.R
import com.codechallenge.home.di.HomeActivityComponent.GameNavigationDependencies
import com.codechallenge.home.di.HomeActivityComponent.RulesNavigationDependencies
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.navigation.NavigatorSubject
import javax.inject.Inject

class HomeNavigationHandler @Inject constructor(
    private val rulesNavigationDependencies: RulesNavigationDependencies,
    private val gameNavigationDependencies: GameNavigationDependencies,
) : NavigatorHandler() {

    override fun <T : NavigatorSubject> navigate(subject: T, vararg args: Any) {
        when (args.firstArg<Int>()) {
            rulesNavigationDependencies.rulesIdentifier -> navigateToHome(subject.getNavigatorContext())
            R.id.buttonRules -> navigateToFragment(
                subject.getNavigatorContext(),
                rulesNavigationDependencies.rulesFragmentName,
                rulesNavigationDependencies.rulesFragmentClass()
            )
            R.id.buttonStart -> navigateToFragment(
                subject.getNavigatorContext(),
                gameNavigationDependencies.gameFragmentName,
                gameNavigationDependencies.gameFragmentClass()
            )
            else -> moveToNext(subject.toParentSubject(), *args)
        }
    }

    internal fun navigateToFragment(
        subject: Context,
        fragmentId: String,
        fragmentClass: Class<Fragment>
    ) {
        (subject as HomeActivity).supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(fragmentId)
            replace(R.id.fragment_container_view, fragmentClass, null, null)
        }
    }

    internal fun navigateToHome(subject: Context) {
        (subject as HomeActivity).onBackPressed()
    }
}
