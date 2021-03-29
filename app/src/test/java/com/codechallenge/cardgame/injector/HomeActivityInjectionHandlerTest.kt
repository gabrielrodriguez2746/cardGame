package com.codechallenge.cardgame.injector

import com.codechallenge.injector.DependedNodeComponentFactory
import com.codechallenge.injector.InjectionNode
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.NodeComponentDependencies
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HomeActivityInjectionHandlerTest {

    private val homeDependencies: NodeComponentDependencies = mockk()
    private val nodeIdentifier: String = "I am the one"
    private val componentFactory: DependedNodeComponentFactory<NodeComponent, NodeComponentDependencies> =
        mockk()

    private lateinit var handler: HomeActivityInjectionHandler

    @BeforeEach
    fun setUp() {
        handler = spyk(
            HomeActivityInjectionHandler(homeDependencies, nodeIdentifier, componentFactory),
            recordPrivateCalls = true
        )
    }

    @Test
    fun `GIVEN manageable subject WHEN plug THEN call inject`() {
        val subject = mockk<InjectionNode<NodeComponent>>(relaxUnitFun = true) {
            every { identifier } returns nodeIdentifier
        }
        val component = mockk<NodeComponent>()
        every { componentFactory.create(homeDependencies) } returns component
        handler.plug(subject)

        verify(exactly = 1) { subject.inject(component) }
    }

    @Test
    fun `GIVEN no manageable subject WHEN plug THEN do not call inject`() {
        val subject = mockk<InjectionNode<NodeComponent>>(relaxUnitFun = true, relaxed = true)
        assertThrows<IllegalStateException> {
            handler.plug(subject)
        }

        verify(exactly = 0) { subject.inject(any()) }
    }

    @Test
    fun `GIVEN no manageable subject subject WHEN unplug THEN do not release memory`() {
        val subject = mockk<InjectionNode<NodeComponent>>(relaxUnitFun = true, relaxed = true)

        assertThrows<IllegalStateException> {
            handler.unplug(subject)
        }
    }
}
