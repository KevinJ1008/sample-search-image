package com.kevinj1008.samplesearchimage.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kevinj1008.base.base.BaseFragment
import com.kevinj1008.samplesearchimage.databinding.FragmentSearchResultBinding

class ImageResultFragment : BaseFragment<FragmentSearchResultBinding>() {

    override val bindingInflater: (inflater: LayoutInflater,
                                   container: ViewGroup?,
                                   attachToRoot: Boolean
    ) -> FragmentSearchResultBinding = FragmentSearchResultBinding::inflate


}