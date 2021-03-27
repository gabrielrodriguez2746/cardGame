package com.codechallenge.home.navigation

import androidx.fragment.app.Fragment
import com.codechallenge.navigation.NavigatorHandler

class HomeNavigationHandler(private val rulesIdentifier: Int) : NavigatorHandler() {

    override fun <T : Any> navigate(subject: T, sourceIdentifier: Int?) {
        when {
            subject is Fragment && rulesIdentifier == sourceIdentifier -> navigateToHome(subject)
            else -> moveToNext(subject, sourceIdentifier)
        }
    }

    internal fun navigateToHome(subject: Fragment) {
        (subject.requireActivity()).onBackPressed()
    }
}
