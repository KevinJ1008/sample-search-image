package com.kevinj1008.samplesearchimage

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
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
        resetRootWindow()
    }

    /**
     * To make AutoCompleteTextView's dialog won't overlap by keyboard
     */
    private fun resetRootWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding?.root?.setPaddingRelative(
            binding?.root?.paddingStart ?: 0,
            getStatusBarHeight(),
            binding?.root?.paddingEnd ?: 0,
            binding?.root?.paddingBottom ?: 0)
    }

    /**
     * Get Android system status bar height
     */
    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}