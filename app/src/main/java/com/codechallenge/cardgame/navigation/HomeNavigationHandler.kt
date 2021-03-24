package com.codechallenge.cardgame.navigation

import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.codechallenge.cardgame.R
import com.codechallenge.home.presentation.HomeFragment
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.rules.presentation.RulesFragment
import javax.inject.Inject

class HomeNavigationHandler @Inject constructor() : NavigatorHandler() {

    override fun <T : Any> navigate(subject: T, sourceIdentifier: Int?) {
        if (subject is HomeFragment && sourceIdentifier == R.id.buttonRules) {
            navigateToRules(subject)
        } else {
            moveToNext(subject, sourceIdentifier)
        }
    }

    internal fun navigateToRules(subject: HomeFragment) {
        (subject.requireActivity()).supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RulesFragment>(R.id.fragment_container_view)
        }
    }
}
