package com.codechallenge.cardgame.navigation

import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.landing.presentation.LandingActivity
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.rules.presentation.RulesActivity
import javax.inject.Inject

class LandingNavigationHandler @Inject constructor(
    private val localStorageDataSource: LocalStorageDataSource,
    private val intentDelegate: IntentDelegate
) : NavigatorHandler() {

    private val hasSeenRules get() = localStorageDataSource.getPreference(HAS_SEEN_RULES, false)

    override fun <T : Any> navigate(subject: T) {
        if (subject is LandingActivity) {
            if (hasSeenRules) {
                // TODO Navigate home
            } else {
                subject.startActivity(intentDelegate.getIntent(subject, RulesActivity::class.java))
                subject.finish()
            }
        } else {
            moveToNext(subject)
        }
    }

}