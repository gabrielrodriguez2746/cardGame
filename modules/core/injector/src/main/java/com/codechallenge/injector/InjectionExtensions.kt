package com.codechallenge.injector

import android.app.Activity
import androidx.fragment.app.Fragment

fun <T : NodeComponent> Activity.plug(subject: InjectionNode<T>) {
    getParentInjectionHandler().plug(subject)
}

fun <T : NodeComponent> Activity.unplug(subject: InjectionNode<T>) {
    getParentInjectionHandler().unplug(subject)
}

fun <T : NodeComponent> Fragment.plug(subject: InjectionNode<T>) {
    (requireActivity() as InjectionProvider).getInjectionHandler().plug(subject)
}

fun <T : NodeComponent> Fragment.unplug(subject: InjectionNode<T>) {
    (requireActivity() as InjectionProvider).getInjectionHandler().unplug(subject)
}

fun Activity.getParentInjectionHandler(): InjectionHandler {
    return (applicationContext as InjectionProvider).getInjectionHandler()
}
