package com.codechallenge.rules.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codechallenge.navigation.navigateNext
import com.codechallenge.rules.R
import com.codechallenge.rules.databinding.FragmentRulesBinding

class RulesFragment : Fragment() {

    private var _binding: FragmentRulesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRulesBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.buttonNext?.setOnClickListener {
            navigateNext(this, R.id.buttonNext)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
