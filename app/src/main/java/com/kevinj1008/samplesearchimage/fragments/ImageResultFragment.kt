package com.kevinj1008.samplesearchimage.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kevinj1008.base.base.BaseFragment
import com.kevinj1008.base.utils.ExceptionStatus
import com.kevinj1008.samplesearchimage.R
import com.kevinj1008.samplesearchimage.controller.ImageResultEpoxyController
import com.kevinj1008.samplesearchimage.databinding.FragmentSearchResultBinding
import com.kevinj1008.samplesearchimage.entity.DisplayMode
import com.kevinj1008.samplesearchimage.viewmodel.SearchImageViewModel
import com.kevinj1008.widget.customview.EmptyView
import com.kevinj1008.widget.listeners.LoadMoreListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageResultFragment : BaseFragment<FragmentSearchResultBinding>() {
    private val viewModel: SearchImageViewModel by viewModel()
    private val args: ImageResultFragmentArgs by navArgs()
    private val epoxyController = ImageResultEpoxyController()
    private var page: Int = 0
    private var layoutEmpty: EmptyView? = null
    private var spanCount = DisplayMode.LIST.value

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentSearchResultBinding = FragmentSearchResultBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewSetting(savedInstanceState)
        registerObservers()
        if (savedInstanceState == null) {
            viewModel.searchImage(keyword = args.keyword)
        } else {
            page = savedInstanceState.getInt(KEY_PAGE, 0)
            epoxyController.onRestoreInstanceState(savedInstanceState)
            epoxyController.requestModelBuild()
            if (binding?.btnDisplayMode?.isVisible == false) binding?.btnDisplayMode?.isVisible = true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SPAN_COUNT, spanCount)
        outState.putInt(KEY_PAGE, page)
        epoxyController.onSaveInstanceState(outState = outState)
    }

    override fun onDestroyView() {
        binding?.recyclerview?.clear()
        super.onDestroyView()
    }

    override fun showCustomErrorView(message: String?, exceptionStatus: ExceptionStatus?) {
        super.showCustomErrorView(message, exceptionStatus)
        if (layoutEmpty == null) {
            layoutEmpty = binding?.stubEmpty?.inflate() as? EmptyView?
            layoutEmpty?.apply {
                setTitle(message)
                if (exceptionStatus is ExceptionStatus.NO_DATA_ERROR) {
                    setButtonVisible(isVisible = false)
                } else {
                    setOnRetryClickListener {
                        layoutEmpty?.isVisible = false
                        viewModel.searchImage(keyword = args.keyword)
                    }
                }
            }
        }
        layoutEmpty?.isVisible = true
    }

    private fun initViewSetting(savedInstanceState: Bundle?) {
        spanCount = getSpanCount(savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        epoxyController.spanCount = spanCount
        layoutManager.spanSizeLookup = epoxyController.spanSizeLookup
        binding?.recyclerview?.apply {
            this.layoutManager = layoutManager
            this.setController(epoxyController)
            val loadMoreListener = object : LoadMoreListener(layoutManager) {
                override var visibleThreshold: Int = 10

                override fun fetchNextPage() {
                    epoxyController.isLoading = true
                    viewModel.fetchNextPage(
                        keyword = args.keyword,
                        page = this@ImageResultFragment.page.toString()
                    )
                }

                override fun isLoading(): Boolean {
                    return epoxyController.isLoading
                }
            }
            this.addOnScrollListener(loadMoreListener)
        }
        binding?.btnDisplayMode?.text = if (spanCount == DisplayMode.LIST.value) {
            activity?.getString(R.string.swap_grid)
        } else {
            activity?.getString(R.string.swap_list)
        }
        binding?.btnDisplayMode?.setOnClickListener {
            spanCount = if (spanCount == DisplayMode.LIST.value) {
                binding?.btnDisplayMode?.text = activity?.getString(R.string.swap_list)
                DisplayMode.GRID.value
            } else {
                binding?.btnDisplayMode?.text = activity?.getString(R.string.swap_grid)
                DisplayMode.LIST.value
            }
            layoutManager.spanCount = spanCount
            binding?.recyclerview?.invalidateItemDecorations()
        }
    }

    private fun registerObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, {
            ++page
            epoxyController.setImages(it)
            if (binding?.btnDisplayMode?.isVisible == false) binding?.btnDisplayMode?.isVisible = true
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                binding?.layoutLoadingContainer?.isVisible = this
            }
        })
        viewModel.loadingError.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.apply {
                epoxyController.isLoading = false
                handleError(this, this@ImageResultFragment)
            }
        })
    }

    private fun getSpanCount(savedInstanceState: Bundle?): Int {
        return savedInstanceState?.getInt(KEY_SPAN_COUNT)
            ?: when (args.displayMode) {
                DisplayMode.LIST,
                DisplayMode.GRID -> {
                    args.displayMode.value
                }
                else -> DisplayMode.LIST.value
            }
    }

    companion object {
        private const val KEY_SPAN_COUNT = "span_count"
        private const val KEY_PAGE = "key_page"
    }
}