package com.codechallenge.cardgame.navigation

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.codechallenge.cardgame.storage.HAS_SEEN_RULES
import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.home.presentation.HomeActivity
import com.codechallenge.rules.presentation.RulesActivity
import com.codechallenge.rules.presentation.RulesFragment
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

class RulesNavigationHandlerTest {

    private val localStorageDataSource: LocalStorageDataSource = mockk(relaxUnitFun = true)
    private val intentDelegate: IntentDelegate = mockk()
    private lateinit var navigationHandler: RulesNavigationHandler

    @BeforeEach
    fun setup() {
        navigationHandler = RulesNavigationHandler(localStorageDataSource, intentDelegate)
    }

    @Test
    fun `GIVEN rules fragment WHEN navigate THEN finish activity root and store preference`() {
        val intentSlot = slot<Intent>()
        val intent = mockk<Intent>()
        val mockedActivity = mockk<FragmentActivity>(relaxUnitFun = true) {
            every { startActivity(capture(intentSlot)) } just runs
        }
        val subject = mockk<RulesFragment> {
            every { requireActivity() } returns mockedActivity
        }
        every { intentDelegate.getIntent(mockedActivity, HomeActivity::class.java) } returns intent

        navigationHandler.navigate(subject)

        Assertions.assertEquals(intentSlot.captured, intent)
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
