package com.codechallenge.home.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.codechallenge.home.R
import com.codechallenge.home.presentation.HomeFragment
import com.codechallenge.navigation.NavigatorHandler

class HomeNavigationHandler(private val rulesIdentifier: Int) : NavigatorHandler() {

    override fun <T : Any> navigate(subject: T, sourceIdentifier: Int?) {
        if (subject is Fragment && rulesIdentifier == sourceIdentifier) {
            navigateHomeFragment(subject)
        } else {
            moveToNext(subject, sourceIdentifier)
        }
    }

    internal fun navigateHomeFragment(subject: Fragment) {
        (subject.requireActivity()).supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<HomeFragment>(R.id.fragment_container_view)
        }
    }
}
