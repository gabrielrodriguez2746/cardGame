package com.codechallenge.cardgame.navigation

import android.content.Intent
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.landing.presentation.LandingActivity
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.navigation.NavigatorSubject
import com.codechallenge.rules.presentation.RulesActivity
import javax.inject.Inject

class LandingNavigationHandler @Inject constructor(
    private val localStorageDataSource: LocalStorageDataSource
) : NavigatorHandler() {

    private val hasSeenRules get() = localStorageDataSource.getPreference(HAS_SEEN_RULES, false)

    override fun <T : NavigatorSubject> navigate(subject: T, vararg args: Any) {
        (subject.getNavigatorContext() as? LandingActivity)?.let { landing ->
            if (hasSeenRules) {
                navigateToHome(landing)
            } else {
                navigateToRules(landing)
            }
        } ?: run {
            moveToNext(subject, *args)
        }
    }

    internal fun navigateToRules(subject: LandingActivity) {
        subject.startActivity(Intent(subject, RulesActivity::class.java))
        subject.finish()
    }

    internal fun navigateToHome(subject: LandingActivity) {
        subject.startActivity(Intent(subject, HomeActivity::class.java))
        subject.finish()
    }
}
