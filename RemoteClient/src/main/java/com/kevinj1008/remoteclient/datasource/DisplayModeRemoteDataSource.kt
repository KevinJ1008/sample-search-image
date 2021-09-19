package com.kevinj1008.remoteclient.datasource

import com.kevinj1008.remoteclient.client.ApiClient
import com.kevinj1008.remoteclient.pojo.DisplayModePOJO
import retrofit2.Response

class DisplayModeRemoteDataSource(
    private val apiClient: ApiClient<Response<DisplayModePOJO>>
) : RemoteDataSource<Response<DisplayModePOJO>> {
    override suspend fun getData(vararg args: Any): Response<DisplayModePOJO>? {
        return apiClient.requestData(*args)
    }
}