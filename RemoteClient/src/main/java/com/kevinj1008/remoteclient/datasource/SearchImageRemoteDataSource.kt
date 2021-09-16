package com.kevinj1008.remoteclient.datasource

import com.kevinj1008.remoteclient.client.ApiClient
import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import retrofit2.Response

class SearchImageRemoteDataSource(
    private val apiClient: ApiClient<Response<SearchImagePOJO>>
) : RemoteDataSource<Response<SearchImagePOJO>> {
    override suspend fun getData(vararg args: Any): Response<SearchImagePOJO>? {
        return apiClient.requestData(*args)
    }
}