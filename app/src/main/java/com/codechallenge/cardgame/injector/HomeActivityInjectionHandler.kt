package com.codechallenge.cardgame.injector

import com.codechallenge.cardgame.di.HomeIdentifier
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionNode
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.NodeComponentDependencies
import com.codechallenge.injector.NodeComponentFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeActivityInjectionHandler @Inject constructor(
    private val homeDependencies: NodeComponentDependencies,
    @HomeIdentifier override var nodeIdentifier: String,
    override var componentFactory: NodeComponentFactory<NodeComponent>
) : InjectionHandler() {

    override fun <T : NodeComponent> plug(subject: InjectionNode<T>) {
        if (subject.canHandle()) {
            withComponent(homeDependencies) { subject.inject(it) }
        } else {
            plugNext(subject)
        }
    }

    override fun <T : NodeComponent> unplug(subject: InjectionNode<T>) {
        if (subject.canHandle()) {
            subject.manageMemoryRelease()
        } else {
            unPlugNext(subject)
        }
    }
}
