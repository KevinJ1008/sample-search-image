package com.kevinj1008.samplesearchimage.usecase.searchimage

import com.kevinj1008.base.utils.Result
import com.kevinj1008.samplesearchimage.entity.ImageEntity

interface SearchImageUseCase {
    suspend fun getImages(keyword: String): Result<List<ImageEntity>>
    suspend fun fetchNextPage(keyword: String, page: String): Result<List<ImageEntity>>
}