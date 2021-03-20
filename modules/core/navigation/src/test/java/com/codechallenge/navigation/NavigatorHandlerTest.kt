package com.codechallenge.navigation

import android.app.Activity
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.KClass

class NavigatorHandlerTest {

    private class ActivityToHandle : Activity()

    @Test
    fun `GIVEN no handler to manage injection WHEN handle injection THEN throw exception and call all handlers`() {
        val subject = mockk<ActivityToHandle>()
        val handlerA = spyk(createHandler<Activity>())
        val handlerB = spyk(createHandler<Activity>())
        val handlerC = spyk(createHandler<Activity>())
        val handler = handlerA.setNext(handlerB).setNext(handlerC)
        assertThrows<IllegalStateException> {
            handler.navigate(subject)
        }
        verify {
            handlerA.navigate(subject)
            handlerB.navigate(subject)
            handlerC.navigate(subject)
        }
    }

    @Test
    fun `GIVEN handler addition and one valid handler WHEN handle injection THEN handler called until handling position`() {
        val subject = mockk<ActivityToHandle>()
        val handlerA = spyk(createHandler<Activity>())
        val handlerB = spyk(createHandler(ActivityToHandle::class))
        val handlerC = spyk(createHandler<Activity>())

        val handler = handlerA.setNext(handlerB).setNext(handlerC)

        handler.navigate(subject)

        verify {
            handlerA.navigate(subject)
            handlerB.navigate(subject)
        }
        verify(exactly = 0) { handlerC.navigate(subject) }
    }

    private fun <R : Activity> createHandler(kClass: KClass<R>? = null) =
        object : NavigatorHandler() {

            override fun <T : Activity> navigate(subject: T) {
                if (kClass?.isInstance(subject) != true) moveToNext(subject)
            }
        }

}