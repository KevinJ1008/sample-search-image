package com.kevinj1008.samplesearchimage.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kevinj1008.base.base.BaseFragment
import com.kevinj1008.base.utils.hideKeyboard
import com.kevinj1008.samplesearchimage.R
import com.kevinj1008.samplesearchimage.databinding.FragmentInputBinding
import com.kevinj1008.samplesearchimage.entity.DisplayMode
import com.kevinj1008.samplesearchimage.viewmodel.InputViewModel
import com.kevinj1008.samplesearchimage.viewmodel.SearchImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputFragment : BaseFragment<FragmentInputBinding>() {

    private val viewModel: InputViewModel by viewModel()
    private var adapter: ArrayAdapter<String>? = null
    private var displayMode = DisplayMode.LIST

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentInputBinding = FragmentInputBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObserver()
        viewModel.getDisplayMode()
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
        binding?.layoutKeywordContainer?.setOnClickListener {
            hideKeyboard()
        }
        initEditText()
    }

    private fun goSearchImage(keyword: String) {
        hideKeyboard()
        val action = InputFragmentDirections.actionInputFragmentToImageResultFragment(
            keyword = keyword,
            displayMode = displayMode)
        findNavController().navigate(action)
        //TODO: save keyword to db, limit to 10 words, if we have 10, just delete the oldest and add new one
    }

    private fun shouldShowEmptyHint(keyword: String): Boolean {
        return if (keyword.trim().isEmpty()) {
            Snackbar.make(requireView(), R.string.empty_input_toast, Snackbar.LENGTH_SHORT).show()
            true
        } else {
            false
        }
    }

    private fun registerObserver() {
        viewModel.displayMode.observe(viewLifecycleOwner, {
            it?.apply {
                displayMode = this
            }
        })
    }

    private fun initEditText() {
        //TODO: get keyword from db
//        val mockList = listOf("yellow", "red", "green", "Orange", "blue", "purple", "black", "white", "violet", "gray")
//        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1, mockList)
//        binding?.editInputName?.setAdapter(adapter)
//        binding?.editInputName?.setOnFocusChangeListener { view, hasFocus ->
//            if (!hasFocus) {
//                binding?.editInputName?.dismissDropDown()
//            }
//        }
//        binding?.editInputName?.setOnClickListener {
//            binding?.editInputName?.showDropDown()
//        }
//        binding?.editInputName?.setOnItemClickListener { parent, view, position, id ->
//            (view as? TextView)?.apply {
//                goSearchImage(keyword = this.text.toString())
//            }
//        }
        binding?.editInputName?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (KeyEvent.ACTION_DOWN == event?.action
                        && KeyEvent.KEYCODE_ENTER == event.keyCode
                        && 0 == event.repeatCount)
            ) {
                (view as? EditText)?.apply {
                    if (shouldShowEmptyHint(this.text.toString())) {
                        return@setOnEditorActionListener false
                    }
                    goSearchImage(keyword = this.text.toString())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }
}