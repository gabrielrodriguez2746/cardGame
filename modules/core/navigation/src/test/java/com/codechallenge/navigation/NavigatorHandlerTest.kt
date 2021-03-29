package com.codechallenge.navigation

import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.KClass

class NavigatorHandlerTest {

    @Test
    fun `GIVEN no handler to manage injection WHEN handle injection THEN throw exception and call all handlers`() {
        val args = 0
        val subject = mockk<NavigatorSubject>()
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler())
        val handlerC = spyk(createHandler())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)
        assertThrows<IllegalStateException> {
            handler.navigate(subject, args)
        }
        verify(exactly = 1) {
            handlerA.navigate(eq(subject), any())
            handlerB.navigate(eq(subject), any())
            handlerC.navigate(eq(subject), any())
        }
    }

    @Test
    fun `GIVEN handler addition and one valid handler WHEN handle injection THEN handler called until handling position`() {
        val args = 0
        val subject = mockk<NavigatorSubject>()
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler(NavigatorSubject::class))
        val handlerC = spyk(createHandler())

        val handler = handlerA.setNext(handlerB).setNext(handlerC)

        handler.navigate(subject, args)

        verify(exactly = 1) {
            handlerA.navigate(eq(subject), any())
            handlerB.navigate(eq(subject), any())
        }
        verify(exactly = 0) { handlerC.navigate(any(), any()) }
    }

    private fun createHandler(kClass: KClass<NavigatorSubject>? = null) =
        object : NavigatorHandler() {

            override fun <T : NavigatorSubject> navigate(subject: T, vararg args: Any) {
                if (kClass?.isInstance(subject) != true) moveToNext(subject, *args)
            }
        }
}
