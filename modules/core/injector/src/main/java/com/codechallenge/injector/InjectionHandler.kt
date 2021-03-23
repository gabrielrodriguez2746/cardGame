package com.codechallenge.injector

abstract class InjectionHandler {

    private var nextHandler: InjectionHandler? = null

    abstract fun <T : NodeComponent> plug(subject: InjectionNode<T>)

    abstract fun <T : NodeComponent> unplug(subject: InjectionNode<T>)

    protected fun <T : NodeComponent> plugNext(subject: InjectionNode<T>) {
        nextHandler?.plug(subject)
            ?: throw IllegalStateException("It's not possible inject at $subject")
    }

    protected fun <T : NodeComponent> unPlugNext(subject: InjectionNode<T>) {
        nextHandler?.unplug(subject)
            ?: throw IllegalStateException("It's not possible inject at $subject")
    }

    fun setNext(handler: InjectionHandler): InjectionHandler {
        nextHandler?.setNext(handler) ?: run {
            nextHandler = handler
        }
        return this
    }
}
