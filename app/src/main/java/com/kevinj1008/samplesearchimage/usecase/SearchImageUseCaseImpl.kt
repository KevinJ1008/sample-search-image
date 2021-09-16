package com.kevinj1008.samplesearchimage.usecase

import com.kevinj1008.base.utils.Result
import com.kevinj1008.remoteclient.model.ApiModel
import com.kevinj1008.remoteclient.pojo.ImageResultPOJO
import com.kevinj1008.samplesearchimage.entity.ImageEntity
import com.kevinj1008.samplesearchimage.repository.SearchImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchImageUseCaseImpl(
    private val searchImageRepository: SearchImageRepository
    ) : SearchImageUseCase {

    override suspend fun getImages(keyword: String): Result<List<ImageEntity>> = withContext(Dispatchers.IO) {
        val keywordModel = ApiModel.SearchImage(keyword = keyword)
        val imagePOJO = searchImageRepository.getSearchImage(keywordModel)
        return@withContext handleEntity(imagePOJO)
    }

    override suspend fun fetchNextPage(page: String): Result<List<ImageEntity>> {
        TODO("Not yet implemented")
    }

    private fun handleEntity(imagePOJO: Result<List<ImageResultPOJO>>): Result<List<ImageEntity>> {
        return if (imagePOJO is Result.Success) {
            Result.Success(imagePOJO.data.map {
                ImageEntity(image = it.previewURL)
            })
        } else {
            imagePOJO as Result.Error
        }
    }
}