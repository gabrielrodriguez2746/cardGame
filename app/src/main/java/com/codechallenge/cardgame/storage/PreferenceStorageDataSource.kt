package com.codechallenge.cardgame.storage

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceStorageDataSource @Inject constructor(
    private val preference: SharedPreferences,
    private val preferenceEditor: SharedPreferences.Editor,
) : LocalStorageDataSource {

    override fun <T: Any> savePreference(key: String, value: T) {
        when {
            value.javaClass.isType<Boolean>() -> preferenceEditor.putBoolean(key, value.toType()).apply()
            value.javaClass.isType<String>() -> preferenceEditor.putString(key, value.toType()).apply()
            value.javaClass.isType<Int>() -> preferenceEditor.putInt(key, value.toType()).apply()
            value.javaClass.isType<Long>() -> preferenceEditor.putLong(key, value.toType()).apply()
            value.javaClass.isType<Float>() -> preferenceEditor.putFloat(key, value.toType()).apply()
            else -> throw IllegalArgumentException("Unable to save $value of type ${value.javaClass}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T: Any> getPreference(key: String, default: T) : T {
        return when {
            default.javaClass.isType<Boolean>() -> preference.getBoolean(key, default.toType()) as T
            default.javaClass.isType<String>() -> preference.getString(key, default.toType()) as T
            default.javaClass.isType<Int>() -> preference.getInt(key, default.toType()) as T
            default.javaClass.isType<Long>() -> preference.getLong(key, default.toType()) as T
            default.javaClass.isType<Float>() -> preference.getFloat(key, default.toType()) as T
            else -> throw IllegalArgumentException("Unable to get storage value with $key of type ${default.javaClass}")
        }
    }

    private inline fun <R, reified T> R.toType() = this as T

    private inline fun <reified T> Class<*>.isType(): Boolean {
        return isAssignableFrom(T::class.java)
    }

}