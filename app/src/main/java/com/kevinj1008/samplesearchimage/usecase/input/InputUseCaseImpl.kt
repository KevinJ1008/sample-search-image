package com.kevinj1008.samplesearchimage.usecase.input

import com.kevinj1008.base.utils.Result
import com.kevinj1008.samplesearchimage.entity.DisplayMode
import com.kevinj1008.samplesearchimage.repository.input.InputRepository

class InputUseCaseImpl(
    private val repository: InputRepository
) : InputUseCase {
    override suspend fun getDisplayMode(): Result<DisplayMode> {
        val result = repository.getDisplayMode()
        return if (result is Result.Success) {
            Result.Success(DisplayMode.getDisplayMode(result.data.displayMode))
        } else {
            //We don't need to care remote success or not for ui, default is list
            Result.Success(DisplayMode.LIST)
        }
    }
}