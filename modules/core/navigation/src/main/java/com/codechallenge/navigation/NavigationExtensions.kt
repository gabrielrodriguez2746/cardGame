package com.codechallenge.navigation

import android.app.Activity
import android.content.Context

fun <T : Activity> Context.navigateNext(subject: T) {
    (applicationContext as NavigationProvider).getNavigator().navigate(subject)
}