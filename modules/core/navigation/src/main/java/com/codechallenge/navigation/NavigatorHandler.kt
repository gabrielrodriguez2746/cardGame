package com.codechallenge.navigation

import android.app.Activity

abstract class NavigatorHandler {

    private var nextHandler: NavigatorHandler? = null

    /**
     * This abstraction should be improved a lot and restricted to fragments, views or activities
     */
    abstract fun <T : NavigatorSubject> navigate(subject: T, vararg args: Any = emptyArray())

    protected fun <T : NavigatorSubject> moveToNext(subject: T, vararg args: Any = emptyArray()) {
        nextHandler?.navigate(subject, args)
            ?: throw IllegalStateException("It's not possible navigate from $subject")
    }

    protected inline fun <reified T> Array<out Any>.firstArg(): T = get(0) as T

    protected fun NavigatorSubject.toParentSubject() =
        (getNavigatorContext() as Activity).navigationSubject()

    /**
     * This method act adding a next injection handler to the queue.
     * Considering this implies the order in the addition care. The last handler added it will be the last consider
     * from the iteration Having HandlerA() + HandlerB() + HandlerC() -> HandlerA will try to perform
     * the navigation and if it is not able to do it it will move to HandlerB and
     * so on. Until there is no next handler available
     *
     * @property handler handler to be queue in the navigation handling
     */
    fun setNext(handler: NavigatorHandler): NavigatorHandler {
        nextHandler?.setNext(handler) ?: run {
            nextHandler = handler
        }
        return this
    }
}
