package com.codechallenge.cardgame.di.storage

import com.codechallenge.cardgame.storage.LocalStorageDataSource
import com.codechallenge.cardgame.storage.PreferenceStorageDataSource
import dagger.Binds
import dagger.Module
import dagger.Reusable

@Module(includes = [PreferenceModule::class])
abstract class LocalStorageModule {

    @Binds
    @Reusable
    abstract fun bindPreferenceStorageDataSource(
        preference: PreferenceStorageDataSource
    ): LocalStorageDataSource

}



