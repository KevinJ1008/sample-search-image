package com.kevinj1008.samplesearchimage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kevinj1008.base.base.BaseFragment
import com.kevinj1008.base.utils.hideKeyboard
import com.kevinj1008.samplesearchimage.R
import com.kevinj1008.samplesearchimage.databinding.FragmentInputBinding

class InputFragment : BaseFragment<FragmentInputBinding>() {

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentInputBinding = FragmentInputBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: could do remote setting
        initView()
    }

    override fun onResume() {
        super.onResume()
        binding?.btnInput?.isClickable = true
    }

    override fun onPause() {
        super.onPause()
        binding?.btnInput?.isClickable = false
    }

    private fun initView() {
        binding?.btnInput?.setOnClickListener {
            val keyword = binding?.editInputName?.text?.toString() ?: ""
            if (shouldShowEmptyHint(keyword = keyword)) {
                return@setOnClickListener
            }
            goSearchImage(keyword = keyword)
        }
        binding?.editInputName?.setOnEditorActionListener { view, actionId, event ->
            return@setOnEditorActionListener false
        }
        binding?.editInputName?.setOnFocusChangeListener { view, hasFocus ->
            //TODO: complete spinner
            //TODO: change edittext to autoCompleteTextView
        }
        binding?.layoutKeywordContainer?.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun goSearchImage(keyword: String) {
        val action = InputFragmentDirections.actionInputFragmentToImageResultFragment(
            keyword = keyword,
            displayMode = null)
        findNavController().navigate(action)
    }

    private fun shouldShowEmptyHint(keyword: String): Boolean {
        return if (keyword.trim().isEmpty()) {
            Snackbar.make(requireView(), R.string.empty_input_toast, Snackbar.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }
}