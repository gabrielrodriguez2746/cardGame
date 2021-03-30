package com.codechallenge.cardgame.navigation

import android.app.Activity
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.navigation.NavigatorSubject
import com.codechallenge.rules.presentation.RulesActivity
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class RulesNavigationHandlerTest {

    private val localStorageDataSource: LocalStorageDataSource = mockk(relaxUnitFun = true)
    private lateinit var navigationHandler: RulesNavigationHandler

    @BeforeEach
    fun setup() {
        navigationHandler = spyk(RulesNavigationHandler(localStorageDataSource))
        every { navigationHandler.navigateToHome(any()) } just runs
    }

    @Test
    fun `GIVEN rules activity subject WHEN navigate THEN navigate home and store preference`() {
        every { localStorageDataSource.getPreference(HAS_SEEN_RULES, any()) } returns false
        val context = mockk<RulesActivity>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        navigationHandler.navigate(subject)

        verify {
            localStorageDataSource.savePreference(HAS_SEEN_RULES, true)
            navigationHandler.navigateToHome(context)
        }
    }

    @Test
    fun `GIVEN no rules activity subject and no more handlers WHEN navigate THEN throw exception`() {
        every { localStorageDataSource.getPreference(HAS_SEEN_RULES, any()) } returns false
        val context = mockk<Activity>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }

        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject)
        }
        verify(exactly = 0) {
            localStorageDataSource.savePreference(any(), any())
        }
    }
}
