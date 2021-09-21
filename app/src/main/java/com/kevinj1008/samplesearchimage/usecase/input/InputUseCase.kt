package com.kevinj1008.samplesearchimage.usecase.input

import com.kevinj1008.base.utils.Result
import com.kevinj1008.samplesearchimage.entity.DisplayMode

interface InputUseCase {
    suspend fun getDisplayMode(): Result<DisplayMode>
    suspend fun getSearchHistory(): Result<List<String>>
    suspend fun saveSearchHistory(newKeyword: String)
}