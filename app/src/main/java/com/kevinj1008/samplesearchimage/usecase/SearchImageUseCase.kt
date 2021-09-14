package com.kevinj1008.samplesearchimage.usecase

import com.kevinj1008.samplesearchimage.entity.ImageEntity

interface SearchImageUseCase {
    suspend fun getImages(keyword: String): List<ImageEntity>
    suspend fun fetchNextPage(page: String): List<ImageEntity>
}