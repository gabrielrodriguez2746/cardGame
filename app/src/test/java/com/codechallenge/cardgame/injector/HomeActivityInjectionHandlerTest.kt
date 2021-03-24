package com.codechallenge.cardgame.injector

import com.codechallenge.home.di.HomeActivityComponent
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.injector.InjectionNode
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HomeActivityInjectionHandlerTest {

    private lateinit var handler: HomeActivityInjectionHandler

    @BeforeEach
    fun setUp() {
        handler = spyk(HomeActivityInjectionHandler())
    }

    @Test
    fun `GIVEN home activity subject WHEN plug THEN call inject`() {
        val subject = mockk<HomeActivity>(relaxUnitFun = true)
        val component = mockk<HomeActivityComponent>()
        every { handler.withComponent(any()) } answers {
            firstArg<(component: HomeActivityComponent) -> Unit>().invoke(component)
        }
        handler.plug(subject)

        verify(exactly = 1) { subject.inject(component) }
    }

    @Test
    fun `GIVEN no home activity subject WHEN plug THEN do not call inject`() {
        val subject = mockk<InjectionNode<HomeActivityComponent>>()
        val component = mockk<HomeActivityComponent>()
        every { handler.withComponent(any()) } answers {
            firstArg<(component: HomeActivityComponent) -> Unit>().invoke(component)
        }
        assertThrows<IllegalStateException> {
            handler.plug(subject)
        }

        verify(exactly = 0) { subject.inject(component) }
    }

    @Test
    fun `GIVEN home activity subject WHEN unplug THEN release memory`() {
        val subject = mockk<HomeActivity>()

        handler.unplug(subject)

        verify(exactly = 1) { handler.releaseComponentMemory() }
    }

    @Test
    fun `GIVEN no home activity subject WHEN unplug THEN do not release memory`() {
        val subject = mockk<InjectionNode<HomeActivityComponent>>()

        assertThrows<IllegalStateException> {
            handler.unplug(subject)
        }

        verify(exactly = 0) { handler.releaseComponentMemory() }
    }
}
