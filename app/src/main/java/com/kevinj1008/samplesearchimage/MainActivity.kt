package com.kevinj1008.samplesearchimage

import android.os.Bundle
import android.view.LayoutInflater
import com.kevinj1008.base.base.BaseViewBindingActivity
import com.kevinj1008.samplesearchimage.databinding.ActivityMainBinding

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    override val bindingInflater: (inflater: LayoutInflater) -> ActivityMainBinding
            = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.logo?.setOnClickListener {
            onBackPressed()
        }
    }
}