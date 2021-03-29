package com.codechallenge.home.navigation

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.codechallenge.home.R
import com.codechallenge.home.di.HomeActivityComponent.GameNavigationDependencies
import com.codechallenge.home.di.HomeActivityComponent.RulesNavigationDependencies
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

internal class HomeNavigationHandlerTest {

    private val rulesNavigationDependencies: RulesNavigationDependencies = mockk()
    private val gameNavigationDependencies: GameNavigationDependencies = mockk()

    private lateinit var navigationHandler: HomeNavigationHandler

    @BeforeEach
    fun setUp() {
        navigationHandler = spyk(
            HomeNavigationHandler(
                rulesNavigationDependencies,
                gameNavigationDependencies
            )
        )
        every { navigationHandler.navigateToFragment(any(), any(), any()) } just runs
        every { navigationHandler.navigateToHome(any()) } just runs
    }

    @Test
    fun `GIVEN no arguments WHEN navigate THEN throw exception`() {
        assertThrows<IndexOutOfBoundsException> {
            navigationHandler.navigate(mockk())
        }
    }

    @Test
    fun `GIVEN rules arguments identifier WHEN navigate THEN navigate to home`() {
        val context = mockk<Context>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        val argumentIdentifier = 1
        every { rulesNavigationDependencies.rulesIdentifier } returns argumentIdentifier

        navigationHandler.navigate(subject, argumentIdentifier)

        verify(exactly = 1) {
            navigationHandler.navigateToHome(context)
        }
    }

    @Test
    fun `GIVEN buttonRules arguments identifier WHEN navigate THEN navigate to rules`() {
        val context = mockk<Context>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        val argumentIdentifier = R.id.buttonRules
        val name = "Rules"
        val fragmentClass = Fragment::class.java
        every { rulesNavigationDependencies.rulesIdentifier } returns -1
        every { rulesNavigationDependencies.rulesFragmentName } returns name
        every { rulesNavigationDependencies.rulesFragmentClass<Fragment>() } returns fragmentClass

        navigationHandler.navigate(subject, argumentIdentifier)

        verify(exactly = 1) {
            navigationHandler.navigateToFragment(context, name, fragmentClass)
        }
    }

    @Test
    fun `GIVEN buttonStart arguments identifier WHEN navigate THEN navigate to rules`() {
        val context = mockk<Context>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        val argumentIdentifier = R.id.buttonStart
        val name = "Game"
        val fragmentClass = Fragment::class.java
        every { rulesNavigationDependencies.rulesIdentifier } returns -1
        every { gameNavigationDependencies.gameFragmentName } returns name
        every { gameNavigationDependencies.gameFragmentClass<Fragment>() } returns fragmentClass

        navigationHandler.navigate(subject, argumentIdentifier)

        verify(exactly = 1) {
            navigationHandler.navigateToFragment(context, name, fragmentClass)
        }
    }

    @Test
    fun `GIVEN arguments without match and no more handlers WHEN navigate THEN throw exception`() {
        val context = mockk<Activity>()
        val subject = mockk<NavigatorSubject>(relaxUnitFun = true) {
            every { getNavigatorContext() } returns context
        }
        every { rulesNavigationDependencies.rulesIdentifier } returns 0
        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject, -1)
        }
        verify(exactly = 0) {
            navigationHandler.navigateToHome(any())
            navigationHandler.navigateToFragment(any(), any(), any())
        }
    }
}
