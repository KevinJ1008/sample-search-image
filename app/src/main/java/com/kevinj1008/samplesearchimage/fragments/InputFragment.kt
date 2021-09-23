package com.kevinj1008.samplesearchimage.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kevinj1008.base.base.BaseFragment
import com.kevinj1008.base.utils.hideKeyboard
import com.kevinj1008.samplesearchimage.R
import com.kevinj1008.samplesearchimage.databinding.FragmentInputBinding
import com.kevinj1008.samplesearchimage.entity.DisplayMode
import com.kevinj1008.samplesearchimage.viewmodel.InputViewModel
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
        viewModel.getSearchHistory()
        initView()
        //Get ime height to prevent dialog overlap by keyboard
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            binding?.root?.setPaddingRelative(
                binding?.root?.paddingStart ?: 0,
                binding?.root?.paddingTop ?: 0,
                binding?.root?.paddingEnd ?: 0,
                imeHeight
            )
            insets
        }
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
            goSearchImage(keyword = keyword)
        }
        binding?.layoutKeywordContainer?.setOnClickListener {
            hideKeyboard()
        }
        initEditText()
    }

    private fun goSearchImage(keyword: String) {
        hideKeyboard()
        val searchWord = keyword.trim()
        if (shouldShowEmptyHint(searchWord)) {
            return
        }
        val action = InputFragmentDirections.actionInputFragmentToImageResultFragment(
            keyword = searchWord,
            displayMode = displayMode)
        findNavController().navigate(action)
        viewModel.saveSearchHistory(searchWord)
    }

    private fun shouldShowEmptyHint(keyword: String): Boolean {
        return if (keyword.isEmpty()) {
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
        viewModel.searchHistory.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                //set spinner
                initSpinner(it)
            }
        })
    }

    private fun initEditText() {
        binding?.editInputName?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (KeyEvent.ACTION_DOWN == event?.action
                        && KeyEvent.KEYCODE_ENTER == event.keyCode
                        && 0 == event.repeatCount)
            ) {
                (view as? EditText)?.apply {
                    goSearchImage(keyword = this.text.toString())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        //Fix EditText focus issue which recent Android version won't have focus after init view
        binding?.editInputName?.requestFocus()
    }

    private fun initSpinner(list: List<String>) {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1, list)
        binding?.editInputName?.setAdapter(adapter)
        binding?.editInputName?.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                binding?.editInputName?.dismissDropDown()
            }
        }
        binding?.editInputName?.setOnClickListener {
            binding?.editInputName?.showDropDown()
        }
        binding?.editInputName?.setOnItemClickListener { parent, view, position, id ->
            (view as? TextView)?.apply {
                goSearchImage(keyword = this.text.toString())
            }
        }
    }
}