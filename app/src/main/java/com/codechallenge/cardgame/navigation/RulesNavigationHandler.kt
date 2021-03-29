package com.codechallenge.cardgame.navigation

import android.content.Intent
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.navigation.NavigatorSubject
import com.codechallenge.rules.presentation.RulesActivity
import javax.inject.Inject

class RulesNavigationHandler @Inject constructor(
    private val localStorageDataSource: LocalStorageDataSource
) : NavigatorHandler() {

    override fun <T : NavigatorSubject> navigate(subject: T, vararg args: Any) {
        val context = subject.getNavigatorContext()
        if (context is RulesActivity) {
            storeHasSeenRules()
            navigateToHome(context)
        } else {
            moveToNext(subject, *args)
        }
    }

    private fun storeHasSeenRules() {
        localStorageDataSource.savePreference(HAS_SEEN_RULES, true)
    }

    internal fun navigateToHome(subject: RulesActivity) {
        with(subject) {
            startActivity(Intent(subject, HomeActivity::class.java))
            finish()
        }
    }
}
