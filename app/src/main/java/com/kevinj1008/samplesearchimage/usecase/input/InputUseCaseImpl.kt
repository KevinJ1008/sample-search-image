package com.kevinj1008.samplesearchimage.usecase.input

import com.kevinj1008.base.utils.Result
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO
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

    override suspend fun getSearchHistory(): Result<List<String>> {
        val result = repository.getSearchHistory()
        return if (result is Result.Success) {
            Result.Success(result.data.map {
                it.keyword
            })
        } else {
            result as Result.Error
        }
    }

    override suspend fun saveSearchHistory(newKeyword: String) {
        val newSearchHistory = SearchHistoryPOJO(keyword = newKeyword)
        repository.saveSearchHistory(newSearchHistoryPOJO = newSearchHistory)
    }
}