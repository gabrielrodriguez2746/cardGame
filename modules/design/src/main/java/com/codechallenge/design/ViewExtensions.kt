package com.codechallenge.design

import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getThemeColorStateList(@AttrRes colorAttribute: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, getThemeColorResource(colorAttribute))
}

fun View.show() {
    visibility = VISIBLE
}

fun View.hide() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}

@ColorRes
private fun Context.getThemeColorResource(@AttrRes colorAttribute: Int): Int {
    return with(TypedValue()) {
        theme.resolveAttribute(colorAttribute, this, true)
        resourceId
    }
}
