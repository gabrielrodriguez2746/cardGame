package com.codechallenge.cardgame.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.codechallenge.cardgame.R
import com.codechallenge.game.presentation.GameFragment
import com.codechallenge.home.presentation.HomeFragment
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.rules.presentation.RulesFragment
import javax.inject.Inject

class HomeNavigationHandler @Inject constructor() : NavigatorHandler() {

    override fun <T : Any> navigate(subject: T, sourceIdentifier: Int?) {
        when {
            subject is HomeFragment && sourceIdentifier == R.id.buttonRules -> navigateToRules(subject)
            subject is HomeFragment && sourceIdentifier == R.id.buttonStart -> navigateToGame(subject)
            else -> moveToNext(subject, sourceIdentifier)
        }
    }

    internal fun navigateToRules(subject: HomeFragment) {
        (subject.requireActivity()).supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(RulesFragment::class.java.simpleName)
            replace<RulesFragment>(R.id.fragment_container_view)
        }
    }

    internal fun navigateToGame(subject: Fragment) {
        (subject.requireActivity()).supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(GameFragment::class.java.simpleName)
            replace<GameFragment>(com.codechallenge.home.R.id.fragment_container_view)
        }
    }
}
