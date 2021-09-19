package com.kevinj1008.samplesearchimage.usecase.searchimage

import com.kevinj1008.base.utils.Result
import com.kevinj1008.remoteclient.model.ApiModel
import com.kevinj1008.remoteclient.pojo.ImageResultPOJO
import com.kevinj1008.samplesearchimage.entity.ImageEntity
import com.kevinj1008.samplesearchimage.repository.searchimage.SearchImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchImageUseCaseImpl(
    private val searchImageRepository: SearchImageRepository
    ) : SearchImageUseCase {

    override suspend fun getImages(keyword: String): Result<List<ImageEntity>>
    = withContext(Dispatchers.IO) {
        val keywordModel = ApiModel.SearchImage(keyword = keyword)
        val imagePOJO = searchImageRepository.searchImages(keywordModel)
        return@withContext handleEntity(imagePOJO)
    }

    override suspend fun fetchNextPage(keyword: String, page: String): Result<List<ImageEntity>>
    = withContext(Dispatchers.IO) {
        val fetchModel = ApiModel.FetchPage(keyword = keyword, page = page)
        val imagePOJO = searchImageRepository.searchImages(fetchModel)
        return@withContext handleEntity(imagePOJO)
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