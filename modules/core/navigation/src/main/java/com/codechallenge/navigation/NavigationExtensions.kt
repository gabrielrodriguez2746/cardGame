package com.codechallenge.navigation

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment

fun Activity.navigateNext(vararg args: Any = emptyArray()) {
    getParentNavigator().navigate(navigationSubject(), *args)
}

fun Activity.navigateNext(subject: NavigatorSubject, vararg args: Any = emptyArray()) {
    getParentNavigator().navigate(subject, *args)
}

fun Fragment.navigateNext(vararg args: Any = emptyArray()) {
    navigateNext(navigationSubject(), *args)
}

fun Fragment.navigateNext(subject: NavigatorSubject, vararg args: Any = emptyArray()) {
    (requireActivity() as? NavigationProvider)?.getNavigator()?.navigate(subject, *args) ?: run {
        requireActivity().navigateNext(*args)
    }
}

fun Activity.getParentNavigator(): NavigatorHandler {
    return (applicationContext as NavigationProvider).getNavigator()
}

fun Activity.navigationSubject(): NavigatorSubject {
    return object : NavigatorSubject() {
        override fun getNavigatorContext(): Context = this@navigationSubject
    }
}

fun Fragment.navigationSubject(): NavigatorSubject {
    return object : NavigatorSubject() {
        override fun getNavigatorContext(): Context = this@navigationSubject.requireContext()
    }
}
