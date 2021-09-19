package com.kevinj1008.remoteclient.client

import com.kevinj1008.remoteclient.pojo.DisplayModePOJO
import kotlinx.coroutines.delay
import retrofit2.Response

class DisplayModeApiClient : ApiClient<Response<DisplayModePOJO>> {

    override suspend fun requestData(vararg args: Any): Response<DisplayModePOJO>? {
        //return mock response
        delay(300)
        return Response.success(DisplayModePOJO(displayMode = 1))
    }
}