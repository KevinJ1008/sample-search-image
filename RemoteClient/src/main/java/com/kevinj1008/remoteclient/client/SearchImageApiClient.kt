package com.kevinj1008.remoteclient.client

import com.kevinj1008.remoteclient.model.ApiModel
import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import com.kevinj1008.remoteclient.services.SearchImageService
import retrofit2.Response

class SearchImageApiClient(
    private val searchImageService: SearchImageService
) : ApiClient<Response<SearchImagePOJO>> {

    override suspend fun requestData(vararg args: Any): Response<SearchImagePOJO>? {
        if (args.isEmpty()) {
            return null
        }

        return if (args.getOrNull(0) is ApiModel) {
            when (val searchData = args[0] as ApiModel) {
                is ApiModel.SearchImage -> {
                    searchImageService.getImages(searchData.keyword)
                }
            }
        } else {
            null
        }
    }
}