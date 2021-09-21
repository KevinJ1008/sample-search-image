package com.kevinj1008.samplesearchimage.repository.input

import com.kevinj1008.base.utils.Result
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO
import com.kevinj1008.remoteclient.pojo.DisplayModePOJO

interface InputRepository {
    suspend fun getDisplayMode(): Result<DisplayModePOJO>
    suspend fun getSearchHistory(): Result<List<SearchHistoryPOJO>>
    suspend fun saveSearchHistory(newSearchHistoryPOJO: SearchHistoryPOJO)
}