package com.kevinj1008.samplesearchimage.repository.searchimage

import com.kevinj1008.base.utils.Result
import com.kevinj1008.remoteclient.model.ApiModel
import com.kevinj1008.remoteclient.pojo.ImageResultPOJO

interface SearchImageRepository {
    suspend fun searchImages(key: ApiModel): Result<List<ImageResultPOJO>>
}