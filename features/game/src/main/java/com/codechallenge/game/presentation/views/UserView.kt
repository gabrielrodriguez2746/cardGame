package com.codechallenge.game.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.codechallenge.design.getThemeColorStateList
import com.codechallenge.design.invisible
import com.codechallenge.design.show
import com.codechallenge.game.R
import com.codechallenge.game.databinding.UserViewBinding

class UserView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    @IntDef(PRIMARY, SECONDARY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Type

    private var binding: UserViewBinding? = null

    var name: CharSequence?
        get() = binding?.textViewUserName?.text
        set(value) {
            binding?.textViewUserName?.text = value
        }

    var score: CharSequence?
        get() = binding?.textViewScore?.text
        set(value) {
            val score = value ?: "0"
            binding?.textViewScore?.text = context.getString(R.string.game_player_score, score)
        }

    @get:Type
    @setparam:Type
    var type: Int = PRIMARY
        set(value) {
            when (value) {
                PRIMARY -> setPrimaryUser()
                SECONDARY -> setSecondaryUser()
            }
            field = value
        }

    init {
        binding = UserViewBinding.inflate(LayoutInflater.from(context), this)
        context.withStyledAttributes(
            attrs,
            R.styleable.UserView,
            defStyleAttr
        ) {
            score = getString(R.styleable.UserView_score)
            rotation = getFloat(R.styleable.UserView_android_rotation, 0f)
            name = getString(R.styleable.UserView_android_name)
            type = getInt(R.styleable.UserView_userType, PRIMARY)
        }
    }

    override fun setRotation(rotation: Float) {
        binding?.cardViewUser?.rotation = rotation
        binding?.imageViewAvatar?.rotation = rotation
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    fun hideUserDeck() {
        binding?.cardViewUser?.invisible()
    }

    fun showUserDeck() {
        binding?.cardViewUser?.show()
    }

    private fun setPrimaryUser() {
        binding?.imageViewAvatar?.imageTintList = context.getThemeColorStateList(R.attr.colorPrimary)
        binding?.cardViewUser?.type = CardGameView.PRIMARY
    }

    private fun setSecondaryUser() {
        binding?.imageViewAvatar?.imageTintList = context.getThemeColorStateList(R.attr.colorPrimaryVariant)
        binding?.cardViewUser?.type = CardGameView.SECONDARY
    }

    companion object {

        const val PRIMARY = 0
        const val SECONDARY = 1
    }
}
