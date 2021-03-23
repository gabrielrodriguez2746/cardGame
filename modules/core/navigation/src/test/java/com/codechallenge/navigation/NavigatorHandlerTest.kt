package com.codechallenge.navigation

import android.content.Context
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.KClass

class NavigatorHandlerTest {

    @Test
    fun `GIVEN no handler to manage injection WHEN handle injection THEN throw exception and call all handlers`() {
        val subject = mockk<Context>()
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler())
        val handlerC = spyk(createHandler())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)
        assertThrows<IllegalStateException> {
            handler.navigate(subject)
        }
        verify(exactly = 1) {
            handlerA.navigate(subject)
            handlerB.navigate(subject)
            handlerC.navigate(subject)
        }
    }

    @Test
    fun `GIVEN handler addition and one valid handler WHEN handle injection THEN handler called until handling position`() {
        val subject = mockk<Context>()
        val handlerA = spyk(createHandler())
        val handlerB = spyk(createHandler(Context::class))
        val handlerC = spyk(createHandler())

        val handler = handlerA.setNext(handlerB).setNext(handlerC)

        handler.navigate(subject)

        verify(exactly = 1) {
            handlerA.navigate(subject)
            handlerB.navigate(subject)
        }
        verify(exactly = 0) { handlerC.navigate(subject) }
    }

    private fun createHandler(kClass: KClass<*>? = null) =
        object : NavigatorHandler() {

            override fun <T : Any> navigate(subject: T) {
                if (kClass?.isInstance(subject) != true) moveToNext(subject)
            }
        }
}
