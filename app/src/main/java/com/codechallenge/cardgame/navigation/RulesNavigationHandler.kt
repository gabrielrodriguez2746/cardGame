package com.codechallenge.cardgame.navigation

import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.rules.presentation.RulesFragment
import javax.inject.Inject

class RulesNavigationHandler @Inject constructor(
    private val localStorageDataSource: LocalStorageDataSource,
    private val intentDelegate: IntentDelegate
) : NavigatorHandler() {

    override fun <T : Any> navigate(subject: T) {
        if (subject is RulesFragment) {
            storeHasSeenRules()
            navigateToHome(subject)
        } else {
            moveToNext(subject)
        }
    }

    private fun navigateToHome(subject: RulesFragment) {
        with(subject.requireActivity()) {
            startActivity(intentDelegate.getIntent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun storeHasSeenRules() {
        localStorageDataSource.savePreference(HAS_SEEN_RULES, true)
    }
}
