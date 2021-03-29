package com.codechallenge.home.injector

import com.codechallenge.home.GameIdentifier
import com.codechallenge.injector.InjectionHandler
import com.codechallenge.injector.InjectionNode
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.NodeComponentFactory
import com.codechallenge.injector.PerActivity
import javax.inject.Inject

@PerActivity
class GameFragmentInjectionHandler @Inject constructor(
    @GameIdentifier override var nodeIdentifier: String,
    override var componentFactory: NodeComponentFactory<NodeComponent>
) : InjectionHandler() {

    override fun <T : NodeComponent> plug(subject: InjectionNode<T>) {
        if (subject.canHandle()) {
            withComponent { subject.inject(it) }
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
