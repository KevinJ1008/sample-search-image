package com.kevinj1008.remoteclient.services

import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SearchImageService {
    @GET(PATH)
    suspend fun getImages(@Query(KEYWORD) keyword: String): Response<SearchImagePOJO>

    @GET(PATH)
    suspend fun fetchNextPage(@Query(KEYWORD) keyword: String,
                              @Query(PAGE) page: String): Response<SearchImagePOJO>

    companion object {
        private const val PATH = "/api"
        private const val KEYWORD = "q"
        private const val PAGE = "page"
    }
}