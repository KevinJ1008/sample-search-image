package com.kevinj1008.samplesearchimage.usecase

import com.kevinj1008.base.utils.Result
import com.kevinj1008.samplesearchimage.entity.ImageEntity

interface SearchImageUseCase {
    suspend fun getImages(keyword: String): Result<List<ImageEntity>>
    suspend fun fetchNextPage(page: String): Result<List<ImageEntity>>
}