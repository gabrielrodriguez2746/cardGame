package com.codechallenge.injector

abstract class InjectionHandler {

    abstract fun <T : NodeComponent> plug(subject: InjectionNode<T>)

    abstract fun <T : NodeComponent> unplug(subject: InjectionNode<T>)

    fun setNext(handler: InjectionHandler): InjectionHandler {
        nextHandler?.setNext(handler) ?: run {
            nextHandler = handler
        }
        return this
    }

    protected abstract var nodeIdentifier: String

    protected abstract var componentFactory: NodeComponentFactory<NodeComponent>

    protected fun <T : NodeComponent> plugNext(subject: InjectionNode<T>) {
        nextHandler?.plug(subject)
            ?: throw IllegalStateException("It's not possible inject at $subject")
    }

    protected fun <T : NodeComponent> unPlugNext(subject: InjectionNode<T>) {
        nextHandler?.unplug(subject)
            ?: throw IllegalStateException("It's not possible inject at $subject")
    }

    protected fun InjectionNode<*>.canHandle(): Boolean = nodeIdentifier == identifier

    protected fun InjectionNode<*>.manageMemoryRelease() {
        if (this is SaveStateInjectionNode && !saveState) {
            releaseComponentMemory()
        } else if (this !is SaveStateInjectionNode) {
            releaseComponentMemory()
        }
    }

    protected fun withComponent(dependencies: NodeComponentDependencies? = null, block: (component: NodeComponent) -> Unit) {
        component?.let {
            block.invoke(it)
        } ?: dependencies?.let {
            component = componentFactory.toTypedFactory<DependedNodeComponentFactory<NodeComponent, NodeComponentDependencies>>()
                .create(it).also(block)
        } ?: run {
            component = componentFactory.toTypedFactory<IndependentNodeComponentFactory<NodeComponent>>()
                .create().also(block)
        }
    }

    private var component: NodeComponent? = null
    private var nextHandler: InjectionHandler? = null

    private fun releaseComponentMemory() { component = null }

    private inline fun <reified T : NodeComponentFactory<*>> NodeComponentFactory<NodeComponent>.toTypedFactory(): T {
        return this as T
    }
}
