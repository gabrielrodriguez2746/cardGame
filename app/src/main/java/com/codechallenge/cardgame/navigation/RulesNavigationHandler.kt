package com.codechallenge.cardgame.navigation

import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.rules.presentation.RulesFragment
import javax.inject.Inject

class RulesNavigationHandler @Inject constructor(
    private val localStorageDataSource: LocalStorageDataSource
) : NavigatorHandler() {

    override fun <T : Any> navigate(subject: T) {
        if (subject is RulesFragment) {
            localStorageDataSource.savePreference(HAS_SEEN_RULES, true)
            // TODO Navigate home
            subject.requireActivity().finish()
        } else {
            moveToNext(subject)
        }
    }
}
