package com.codechallenge.home.navigation

import androidx.fragment.app.Fragment
import com.codechallenge.home.presentation.HomeActivity
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
        navigationHandler = spyk(HomeNavigationHandler(RULES_IDENTIFIER))
    }

    @Nested
    @DisplayName("GIVEN subject as fragment and ")
    inner class WithHomeFragment {

        private val subject = mockk<Fragment>()

        @Test
        fun `no match with rules identifier WHEN navigate THEN throw exception`() {
            assertThrows<IllegalStateException> {
                navigationHandler.navigate(subject, -1)
            }
            verify(exactly = 0) {
                navigationHandler.navigateHomeFragment(any())
            }
        }

        @Test
        fun `match with rules identifier WHEN navigate THEN navigate to home`() {

            every { navigationHandler.navigateHomeFragment(any()) } just runs

            navigationHandler.navigate(subject, RULES_IDENTIFIER)

            verify(exactly = 1) {
                navigationHandler.navigateHomeFragment(subject)
            }
        }
    }

    @Test
    fun `GIVEN no fragment as subject and no more handlers WHEN navigate THEN throw exception`() {
        val subject = mockk<HomeActivity>()

        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject, RULES_IDENTIFIER)
        }
        verify(exactly = 0) {
            navigationHandler.navigateHomeFragment(any())
        }
    }

    companion object {
        private const val RULES_IDENTIFIER = 1
    }
}
