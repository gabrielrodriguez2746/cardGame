package com.codechallenge.navigation

import android.app.Activity
import androidx.fragment.app.Fragment

fun <T : Activity> Activity.navigateNext(subject: T) {
    getParentNavigator().navigate(subject)
}

fun <T : Fragment> Fragment.navigateNext(subject: T) {
    (requireActivity() as NavigationProvider).getNavigator().navigate(subject)
}

fun Activity.getParentNavigator(): NavigatorHandler {
    return (applicationContext as NavigationProvider).getNavigator()
}