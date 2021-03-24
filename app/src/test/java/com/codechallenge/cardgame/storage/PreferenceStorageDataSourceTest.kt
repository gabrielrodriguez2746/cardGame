package com.codechallenge.cardgame.storage

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PreferenceStorageDataSourceTest {

    private val preference: SharedPreferences = mockk {
        every { getBoolean(any(), any()) } answers { secondArg() }
        every { getString(any(), any()) } answers { secondArg<String>() }
        every { getInt(any(), any()) } answers { secondArg() }
        every { getLong(any(), any()) } answers { secondArg() }
        every { getFloat(any(), any()) } answers { secondArg() }
    }
    private val preferenceEditor: SharedPreferences.Editor = mockk(relaxed = true) {
        every { putBoolean(any(), any()) } returns this
        every { putString(any(), any()) } returns this
        every { putInt(any(), any()) } returns this
        every { putLong(any(), any()) } returns this
        every { putFloat(any(), any()) } returns this
    }

    private lateinit var storageDataSource: PreferenceStorageDataSource

    @BeforeEach
    fun setup() {
        storageDataSource = PreferenceStorageDataSource(preference, preferenceEditor)
    }

    @Test
    fun `GIVEN all valid types of values WHEN savePreference THEN call editor`() {
        val key = "key"
        val boolean = true
        val string = "Hello"
        val int = 1
        val long = 2L
        val float = 3.5f
        listOf<Any>(boolean, string, int, long, float).forEach {
            storageDataSource.savePreference(key, it)
        }

        verify(exactly = 5) {
            preferenceEditor.apply()
        }
        verify(exactly = 1) {
            preferenceEditor.putBoolean(key, boolean)
            preferenceEditor.putString(key, string)
            preferenceEditor.putInt(key, int)
            preferenceEditor.putLong(key, long)
            preferenceEditor.putFloat(key, float)
        }
    }

    @Test
    fun `GIVEN invalid of values WHEN savePreference THEN throw exception`() {
        val key = "key"
        assertThrows<IllegalArgumentException> {
            storageDataSource.savePreference(key, listOf<Any>())
        }
    }

    @Test
    fun `GIVEN all valid types of values WHEN getPreference THEN return value`() {
        val key = "key"
        val boolean = true
        val string = "Hello"
        val int = 1
        val long = 2L
        val float = 3.5f
        listOf<Any>(boolean, string, int, long, float).forEach { value ->
            Assertions.assertEquals(storageDataSource.getPreference(key, value), value)
        }
    }

    @Test
    fun `GIVEN invalid of values WHEN getPreference THEN throw exception`() {
        val key = "key"
        assertThrows<IllegalArgumentException> {
            storageDataSource.getPreference(key, listOf<Any>())
        }
    }
}
