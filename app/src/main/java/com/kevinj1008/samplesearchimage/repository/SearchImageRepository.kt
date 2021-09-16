package com.kevinj1008.samplesearchimage.repository

import com.kevinj1008.base.utils.Result
import com.kevinj1008.remoteclient.model.ApiModel
import com.kevinj1008.remoteclient.pojo.ImageResultPOJO

interface SearchImageRepository {
    suspend fun getSearchImage(keyword: ApiModel.SearchImage): Result<List<ImageResultPOJO>>
//    suspend fun fetchNextPage(page: String): List<ImageResultPOJO>
}