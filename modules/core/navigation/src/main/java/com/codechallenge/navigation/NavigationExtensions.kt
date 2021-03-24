package com.codechallenge.navigation

import android.app.Activity
import androidx.fragment.app.Fragment

fun <T : Activity> Activity.navigateNext(subject: T, sourceIdentifier: Int? = null) {
    getParentNavigator().navigate(subject, sourceIdentifier)
}

fun <T : Fragment> Fragment.navigateNext(subject: T, sourceIdentifier: Int? = null) {
    (requireActivity() as NavigationProvider).getNavigator().navigate(subject, sourceIdentifier)
}

fun Activity.getParentNavigator(): NavigatorHandler {
    return (applicationContext as NavigationProvider).getNavigator()
}
