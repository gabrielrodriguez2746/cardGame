package com.codechallenge.game.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import com.codechallenge.design.hide
import com.codechallenge.design.show
import com.codechallenge.game.R
import com.codechallenge.game.databinding.FragmentGameBinding
import com.codechallenge.game.di.GameFragmentComponent
import com.codechallenge.game.presentation.views.CardGameView
import com.codechallenge.injector.NamedInjectionNode
import com.codechallenge.injector.NodeComponent
import com.codechallenge.injector.SaveStateInjectionNode
import com.codechallenge.injector.plug
import com.codechallenge.injector.toTypedComponent
import com.codechallenge.injector.unplug
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import javax.inject.Inject

class GameFragment : Fragment(), SaveStateInjectionNode<GameFragmentComponent>, NamedInjectionNode {

    override val identifier: String get() = GameFragment::class.java.simpleName
    override var saveState: Boolean = false

    @Inject
    lateinit var summaryFormatter: GameSummaryFormatter

    @Inject
    lateinit var viewModel: GameViewModel

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plug(this)
        binding.buttonReset.setOnClickListener { viewModel.onReset() }
        viewModel.gameState.observe(
            viewLifecycleOwner,
            { gameState ->
                when (gameState) {
                    is GameState.GameStarted -> setInitialState()
                    is GameState.RoundPlayed -> setRoundPlayedState(gameState)
                    is GameState.RoundWinnerCalculated -> showWinnerLabel(gameState.hasPlayerOneWon)
                    is GameState.RoundFinished -> setRoundFinishedState(gameState)
                    is GameState.GameFinished -> setGameFinished(gameState)
                    is GameState.GameRest -> showConfirmationResetDialog()
                }
            }
        )
        if (savedInstanceState == null) {
            viewModel.onStart()
        } else {
            // TODO This should be cleaner and come from the viewModel
            setHitListener()
            binding.userViewPlayerOne.showUserDeck()
            binding.userViewPlayerTwo.showUserDeck()
        }
    }

    private fun setGameFinished(gameState: GameState.GameFinished) {
        invalidateHitListener()
        MaterialAlertDialogBuilder(requireContext(), R.style.ThemeOverlay_CardGameAppTheme_Summary)
            .setTitle(R.string.game_summary_title)
            .setItems(
                with(summaryFormatter) { gameState.summary.toRoundString(requireContext()) },
                null
            )
            .setPositiveButton(R.string.game_summary_positive_button) { dialog, _ ->
                setGameResetState()
                dialog.dismiss()
            }.setCancelable(false)
            .setNegativeButton(R.string.game_summary_negative_button) { dialog, _ ->
                dialog.dismiss()
                // TODO This should be handled with the navigation
                (requireActivity()).onBackPressed()
            }
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unplug(this)
        _binding = null
        saveState = false
    }

    private fun showConfirmationResetDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.game_reset_body)
            .setPositiveButton(R.string.game_reset_positive_button) { dialog, _ ->
                setGameResetState()
                dialog.dismiss()
            }.setCancelable(false)
            .setNegativeButton(R.string.game_reset_negative_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setGameResetState() {
        binding.userViewPlayerOne.hideDiscardDeck()
        binding.userViewPlayerOne.hideUserDeck()
        binding.userViewPlayerOne.score = "0"
        binding.userViewPlayerTwo.hideDiscardDeck()
        binding.userViewPlayerTwo.hideUserDeck()
        binding.userViewPlayerTwo.score = "0"
        viewModel.onStart()
    }

    private fun setRoundFinishedState(state: GameState.RoundFinished) {
        binding.userViewPlayerOne.score = "${state.playerOnePoints}"
        binding.userViewPlayerTwo.score = "${state.playerTwoPoints}"
        val animation = MaterialFadeThrough().apply {
            addListener(
                object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) {
                        setHitListener()
                        viewModel.onNewRoundStart()
                    }
                }
            )
        }
        TransitionManager.beginDelayedTransition(binding.root, animation)
        binding.cardViewPLayerOne.label = null
        binding.cardViewPLayerOne.suit = CardGameView.NONE
        binding.cardViewPLayerTwo.label = null
        binding.cardViewPLayerTwo.suit = CardGameView.NONE
        binding.cardViewPLayerOne.hide()
        binding.cardViewPLayerTwo.hide()
        binding.userViewPlayerOne.showDiscardDeck()
        binding.userViewPlayerTwo.showDiscardDeck()
        binding.userViewPlayerOne.showTopPlayingCard()
        binding.userViewPlayerTwo.showTopPlayingCard()
    }

    private fun setRoundPlayedState(state: GameState.RoundPlayed) {
        invalidateHitListener()
        binding.cardViewPLayerOne.label = state.card1.label
        binding.cardViewPLayerOne.suit = state.card1.suit.suitId
        binding.cardViewPLayerTwo.label = state.card2.label
        binding.cardViewPLayerTwo.suit = state.card2.suit.suitId
        val animation = MaterialFadeThrough().apply {
            addListener(
                object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) = viewModel.onRound()
                }
            )
        }
        TransitionManager.beginDelayedTransition(binding.root, animation)
        binding.userViewPlayerOne.hideTopPlayingCard()
        binding.userViewPlayerTwo.hideTopPlayingCard()
        binding.cardViewPLayerOne.show()
        binding.cardViewPLayerTwo.show()
    }

    private fun setInitialState() {
        binding.textViewNotifications.text = getString(R.string.game_drawing)
        TransitionManager.beginDelayedTransition(
            binding.root,
            MaterialFade().apply {
                addListener(
                    object : TransitionListenerAdapter() {
                        override fun onTransitionEnd(transition: Transition) {
                            hideNotification { setHitListener() }
                        }
                    }
                )
            }
        )
        binding.textViewNotifications.show()
        binding.userViewPlayerOne.showUserDeck()
        binding.userViewPlayerTwo.showUserDeck()
    }

    private fun setHitListener() {
        binding.buttonHit.setOnClickListener {
            viewModel.onHit()
        }
    }

    private fun invalidateHitListener() {
        binding.buttonHit.setOnClickListener(null)
    }

    private fun hideNotification(onAnimationEnd: () -> Unit) {
        val animation = MaterialFade().apply {
            addListener(
                object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) {
                        onAnimationEnd.invoke()
                    }
                }
            )
        }
        TransitionManager.beginDelayedTransition(binding.root, animation)
        binding.textViewNotifications.hide()
    }

    private fun showWinnerLabel(hasPLayerOneWon: Boolean) {
        binding.textViewNotifications.text = if (hasPLayerOneWon) {
            getString(R.string.game_winner_round_message, getString(R.string.game_player_one))
        } else {
            getString(R.string.game_winner_round_message, getString(R.string.game_player_two))
        }
        val animation = MaterialFade().apply {
            addListener(
                object : TransitionListenerAdapter() {
                    override fun onTransitionEnd(transition: Transition) = hideNotification {
                        viewModel.onWinnerCardDisplayed()
                    }
                }
            )
        }
        TransitionManager.beginDelayedTransition(binding.root, animation)
        binding.textViewNotifications.show()
    }

    override fun inject(component: NodeComponent) {
        component.toTypedComponent<GameFragmentComponent>().inject(this)
    }
}
