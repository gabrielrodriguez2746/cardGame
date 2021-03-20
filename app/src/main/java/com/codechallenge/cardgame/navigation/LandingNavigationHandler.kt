package com.codechallenge.cardgame.navigation

import android.app.Activity
import com.codechallenge.navigation.NavigatorHandler
import com.codechallenge.landing.presentation.LandingActivity
import javax.inject.Inject

class LandingNavigationHandler @Inject constructor() : NavigatorHandler() {

    override fun <T : Activity> navigate(subject: T) {
        if (subject is LandingActivity) {
            // TODO Here connect with tutorial or home depending on local storage
        } else {
            moveToNext(subject)
        }
    }


}