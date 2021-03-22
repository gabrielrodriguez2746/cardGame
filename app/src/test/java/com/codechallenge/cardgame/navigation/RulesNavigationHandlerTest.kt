package com.codechallenge.cardgame.navigation

import androidx.fragment.app.FragmentActivity
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.rules.presentation.RulesActivity
import com.codechallenge.rules.presentation.RulesFragment
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RulesNavigationHandlerTest {

    private val localStorageDataSource: LocalStorageDataSource = mockk(relaxUnitFun = true)
    private lateinit var navigationHandler: RulesNavigationHandler

    @BeforeEach
    fun setup() {
        navigationHandler = RulesNavigationHandler(localStorageDataSource)
    }

    @Test
    fun `GIVEN rules fragment WHEN navigate THEN finish activity root and store preference`() {
        val mockedActivity = mockk<FragmentActivity>(relaxUnitFun = true)
        val subject = mockk<RulesFragment> {
            every { requireActivity() } returns mockedActivity
        }
        navigationHandler.navigate(subject)

        verify(exactly = 1) {
            localStorageDataSource.savePreference(HAS_SEEN_RULES, true)
            mockedActivity.finish()
        }
    }

    @Test
    fun `GIVEN no rules fragment as subject and no more handlers WHEN navigate THEN throw exception`() {
        val subject = mockk<RulesActivity>()

        assertThrows<IllegalStateException> {
            navigationHandler.navigate(subject)
        }
        verify(exactly = 0) {
            localStorageDataSource.savePreference(any(), any())
        }
    }
}
