package com.codechallenge.cardgame.navigation

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class IntentDelegate @Inject constructor() {

    fun getIntent(context: Context, kClass: Class<*>): Intent {
        return Intent(context, kClass)
    }

}