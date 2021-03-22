package com.codechallenge.cardgame.di.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object PreferenceModule {

    @Provides
    @Reusable
    fun provideSharedPreference(context: Application): SharedPreferences {
        return context.getSharedPreferences("com.codechallenge.cardgame.localstorage", Context.MODE_PRIVATE)
    }

    @Provides
    @Reusable
    fun provideSharedPreferenceEditor(preference: SharedPreferences): SharedPreferences.Editor {
        return preference.edit()
    }

}