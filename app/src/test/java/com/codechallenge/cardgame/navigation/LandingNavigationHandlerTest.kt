package com.codechallenge.cardgame.navigation

import android.content.Intent
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.landing.presentation.LandingActivity
import com.codechallenge.rules.presentation.RulesActivity
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LandingNavigationHandlerTest {

    private val localStorageDataSource: LocalStorageDataSource = mockk()
    private val intentDelegate: IntentDelegate = mockk()
    private lateinit var navigationHandler: LandingNavigationHandler

    @BeforeEach
    fun setUp() {
        navigationHandler = LandingNavigationHandler(localStorageDataSource, intentDelegate)
    }

    @Test
    fun `GIVEN landing activity subject and has not seen rules WHEN navigate THEN navigate to rules`() {
        val intentSlot = slot<Intent>()
        val intent = mockk<Intent>()
        val subject = mockk<LandingActivity>(relaxUnitFun = true) {
            every { startActivity(capture(intentSlot)) } just runs
        }
        every { intentDelegate.getIntent(subject, RulesActivity::class.java) } returns intent
        every { localStorageDataSource.getPreference(HAS_SEEN_RULES, any()) } returns false
        navigationHandler.navigate(subject)

        Assertions.assertEquals(intentSlot.captured, intent)
        verify { subject.finish() }
    }

    @Test
    fun `GIVEN no landing activity as subject and no more handlers WHEN navigate THEN throw exception`() {
        val subject = mockk<RulesActivity>()

        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject)
        }
        verify(exactly = 0) {
            localStorageDataSource.getPreference(any(), any())
        }
    }
}
