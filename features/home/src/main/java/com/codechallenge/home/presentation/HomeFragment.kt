package com.codechallenge.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codechallenge.home.R
import com.codechallenge.home.databinding.FragmentHomeBinding
import com.codechallenge.navigation.navigateNext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.buttonRules?.setOnClickListener {
            navigateNext(R.id.buttonRules)
        }
        _binding?.buttonStart?.setOnClickListener {
            navigateNext(R.id.buttonStart)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
