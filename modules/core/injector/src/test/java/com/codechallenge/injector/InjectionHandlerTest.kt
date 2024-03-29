package com.codechallenge.injector

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class InjectionHandlerTest {

    @Test
    fun `GIVEN no handler to plug WHEN plug THEN throw exception and call all handlers`() {
        val subject = mockk<InjectionNode<NodeComponent>>(relaxed = true)
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler())
        val handlerC = spyk(createHandler())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)
        assertThrows<IllegalStateException> {
            handler.plug(subject)
        }
        verify(exactly = 1) {
            handlerA.plug(subject)
            handlerB.plug(subject)
            handlerC.plug(subject)
        }
    }

    @Test
    fun `GIVEN no handler to unplug WHEN unplug THEN throw exception and call all handlers`() {
        val subject = mockk<InjectionNode<NodeComponent>>(relaxed = true)
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler())
        val handlerC = spyk(createHandler())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)
        assertThrows<IllegalStateException> {
            handler.unplug(subject)
        }
        verify(exactly = 1) {
            handlerA.unplug(subject)
            handlerB.unplug(subject)
            handlerC.unplug(subject)
        }
    }

    @Test
    fun `GIVEN one valid handler to plug WHEN plug THEN handler called until handling position`() {
        val expectedIdentifier = "I am the one"
        val subject = mockk<InjectionNode<NodeComponent>> {
            every { identifier } returns expectedIdentifier
        }
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler(nodeIdentifier = expectedIdentifier))
        val handlerC = spyk(createHandler())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)

        handler.plug(subject)

        verify(exactly = 1) {
            handlerA.plug(subject)
            handlerB.plug(subject)
        }
        verify(exactly = 0) {
            handlerC.plug(subject)
        }
    }

    @Test
    fun `GIVEN one valid handler to unplug WHEN unplug THEN handler called until handling position`() {
        val expectedIdentifier = "I am the one"
        val subject = mockk<InjectionNode<NodeComponent>> {
            every { identifier } returns expectedIdentifier
        }
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler(nodeIdentifier = expectedIdentifier))
        val handlerC = spyk(createHandler())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)

        handler.unplug(subject)

        verify(exactly = 1) {
            handlerA.unplug(subject)
            handlerB.unplug(subject)
        }
        verify(exactly = 0) {
            handlerC.unplug(subject)
        }
    }

    private fun createHandler(
        nodeIdentifier: String = "nodeIdentifier",
        componentFactory: NodeComponentFactory<NodeComponent> = mockk()
    ): InjectionHandler {
        return object : InjectionHandler() {

            override var nodeIdentifier: String = nodeIdentifier
            override var componentFactory: NodeComponentFactory<NodeComponent> = componentFactory

            override fun <T : NodeComponent> plug(subject: InjectionNode<T>) {
                if (!subject.canHandle()) plugNext(subject)
            }

            override fun <T : NodeComponent> unplug(subject: InjectionNode<T>) {
                if (!subject.canHandle()) unPlugNext(subject)
            }
        }
    }
}
