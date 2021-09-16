package com.kevinj1008.samplesearchimage.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.kevinj1008.base.base.BaseFragment
import com.kevinj1008.samplesearchimage.controller.ImageResultEpoxyController
import com.kevinj1008.samplesearchimage.databinding.FragmentSearchResultBinding
import com.kevinj1008.samplesearchimage.viewmodel.SearchImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageResultFragment : BaseFragment<FragmentSearchResultBinding>() {
    private val viewModel: SearchImageViewModel by viewModel()
    private val args: ImageResultFragmentArgs by navArgs()
    private val epoxyController = ImageResultEpoxyController()
    private var page: Int = 0

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentSearchResultBinding = FragmentSearchResultBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewSetting()
        registerObservers()
        if (savedInstanceState == null) {
            viewModel.searchImage(keyword = args.keyword)
            //TODO: set display mode
        } else {
            epoxyController.onRestoreInstanceState(savedInstanceState)
            epoxyController.requestModelBuild()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState = outState)
    }

    override fun onDestroyView() {
        binding?.recyclerview?.clear()
        super.onDestroyView()
    }

    private fun initViewSetting() {
        binding?.recyclerview?.setController(epoxyController)
    }

    private fun registerObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, {
            ++page
            epoxyController.setImages(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                binding?.layoutLoadingContainer?.isVisible = this
            }
        })
        viewModel.loadingError.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                Log.d("ImageResultFragment", "error is: $it")
                //TODO: complete error view handle
            }
        })
    }
}