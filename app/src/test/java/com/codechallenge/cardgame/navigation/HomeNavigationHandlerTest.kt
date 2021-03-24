package com.codechallenge.cardgame.navigation

import com.codechallenge.cardgame.R
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.home.presentation.HomeFragment
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class HomeNavigationHandlerTest {

    private lateinit var navigationHandler: HomeNavigationHandler

    @BeforeEach
    fun setUp() {
        navigationHandler = spyk(HomeNavigationHandler())
    }

    @Nested
    @DisplayName("GIVEN subject as home fragment and ")
    inner class WithHomeFragment {

        private val subject = mockk<HomeFragment>()

        @Test
        fun `no rules identifier WHEN navigate THEN throw exception`() {
            assertThrows<IllegalStateException> {
                navigationHandler.navigate(subject, -1)
            }
            verify(exactly = 0) {
                navigationHandler.navigateToRules(any())
            }
        }

        @Test
        fun `rules identifier WHEN navigate THEN navigate to rules`() {

            every { navigationHandler.navigateToRules(any()) } just runs

            navigationHandler.navigate(subject, R.id.buttonRules)

            verify(exactly = 1) {
                navigationHandler.navigateToRules(subject)
            }
        }
    }

    @Test
    fun `GIVEN no home fragment as subject and no more handlers WHEN navigate THEN throw exception`() {
        val subject = mockk<HomeActivity>()

        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject)
        }
        verify(exactly = 0) {
            navigationHandler.navigateToRules(any())
        }
    }
}
