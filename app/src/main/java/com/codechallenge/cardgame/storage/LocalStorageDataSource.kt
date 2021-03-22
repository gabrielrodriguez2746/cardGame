package com.codechallenge.cardgame.storage

interface LocalStorageDataSource {

    fun <T : Any> savePreference(key: String, value: T)

    fun <T : Any> getPreference(key: String, default: T): T
}
