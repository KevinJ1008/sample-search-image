package com.kevinj1008.remoteclient.services

import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchImageService {
    @GET
    suspend fun getImages(@Query("q") keyword: String): Response<SearchImagePOJO>

    @GET
    suspend fun fetchNextPage(@Query("page") page: String): Response<SearchImagePOJO>
}