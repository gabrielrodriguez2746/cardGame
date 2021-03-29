package com.codechallenge.cardgame.navigation

import android.app.Activity
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.landing.presentation.LandingActivity
import com.codechallenge.navigation.NavigatorSubject
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class LandingNavigationHandlerTest {

    private val localStorageDataSource: LocalStorageDataSource = mockk()
    private lateinit var navigationHandler: LandingNavigationHandler

    @BeforeEach
    fun setUp() {
        navigationHandler = spyk(LandingNavigationHandler(localStorageDataSource))
        every { navigationHandler.navigateToHome(any()) } just runs
        every { navigationHandler.navigateToRules(any()) } just runs
    }

    @Test
    fun `GIVEN landing activity subject and has not seen rules WHEN navigate THEN navigate to rules`() {
        every { localStorageDataSource.getPreference(HAS_SEEN_RULES, any()) } returns false
        val context = mockk<LandingActivity>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        navigationHandler.navigate(subject)

        verify { navigationHandler.navigateToRules(context) }
    }

    @Test
    fun `GIVEN landing activity subject and has seen rules WHEN navigate THEN navigate to home`() {
        every { localStorageDataSource.getPreference(HAS_SEEN_RULES, any()) } returns true
        val context = mockk<LandingActivity>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        navigationHandler.navigate(subject)

        verify { navigationHandler.navigateToHome(context) }
    }

    @Test
    fun `GIVEN no landing activity as subject and no more handlers WHEN navigate THEN throw exception`() {
        val context = mockk<Activity>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }

        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject)
        }
        verify(exactly = 0) {
            localStorageDataSource.getPreference(any(), any())
        }
    }
}
