package com.codechallenge.game.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.codechallenge.design.getThemeColorStateList
import com.codechallenge.design.hide
import com.codechallenge.design.show
import com.codechallenge.game.R
import com.codechallenge.game.databinding.CardGameViewBinding
import com.google.android.material.shape.MaterialShapeDrawable

class CardGameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @IntDef(HEARD, CLUB, DIAMOND, SPADE, NONE)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Suit

    @IntDef(PRIMARY, SECONDARY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Type

    private var _binding: CardGameViewBinding? = null

    @get:Suit
    @setparam:Suit
    var suit: Int = NONE
        set(value) {
            when (value) {
                HEARD -> cardImageResource = R.drawable.ic_heart
                CLUB -> cardImageResource = R.drawable.ic_club
                DIAMOND -> cardImageResource = R.drawable.ic_diamond
                SPADE -> cardImageResource = R.drawable.ic_spades
                NONE -> cardImageResource = null
            }
            field = value
        }

    @get:Type
    @setparam:Type
    var type: Int = PRIMARY
        set(value) {
            when (value) {
                PRIMARY -> setPrimaryConfiguration()
                SECONDARY -> setSecondaryConfiguration()
                BACK -> setBackConfiguration()
            }
            field = value
        }

    @DrawableRes
    var cardImageResource: Int? = null
        set(value) {
            value?.let {
                _binding?.imageViewCard?.show()
                _binding?.imageViewCard?.setImageDrawable(ContextCompat.getDrawable(context, it))
            } ?: run {
                _binding?.imageViewCard?.hide()
            }
            field = value
        }

    var label: CharSequence?
        get() = _binding?.textViewCard?.text
        set(value) {
            value?.let {
                _binding?.textViewCard?.show()
                _binding?.textViewCard?.text = it
            } ?: run {
                _binding?.textViewCard?.hide()
            }
        }

    init {
        _binding = CardGameViewBinding.inflate(LayoutInflater.from(context), this)
        context.withStyledAttributes(
            attrs,
            R.styleable.CardGameView,
            defStyleAttr,
            defStyleRes = R.style.CustomView_CardGameView
        ) {
            val materialShapeDrawable = MaterialShapeDrawable(
                context,
                attrs,
                R.attr.shapeAppearance,
                R.style.CustomView_CardGameView
            )
            elevation = getDimension(R.styleable.CardGameView_android_elevation, 0f)
            label = getString(R.styleable.CardGameView_label)
            suit = getInt(R.styleable.CardGameView_suit, NONE)
            background = materialShapeDrawable
            type = getInt(R.styleable.CardGameView_cardType, PRIMARY)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    private fun setSecondaryConfiguration() {
        backgroundTintList = context.getThemeColorStateList(R.attr.colorPrimaryVariant)
        _binding?.imageViewCard?.imageTintList = context.getThemeColorStateList(R.attr.colorPrimary)
    }

    private fun setPrimaryConfiguration() {
        backgroundTintList = context.getThemeColorStateList(R.attr.colorPrimary)
        _binding?.imageViewCard?.imageTintList =
            context.getThemeColorStateList(R.attr.colorPrimaryVariant)
    }

    private fun setBackConfiguration() {
        _binding?.imageViewCard?.hide()
        _binding?.textViewCard?.hide()
        backgroundTintList = context.getThemeColorStateList(R.attr.colorPrimaryDark)
    }

    companion object {
        const val HEARD = 0
        const val CLUB = 1
        const val DIAMOND = 2
        const val SPADE = 3
        const val NONE = 4

        const val PRIMARY = 0
        const val SECONDARY = 1
        const val BACK = 2
    }
}
