package com.kevinj1008.remoteclient

import com.kevinj1008.remoteclient.interceptor.KeyInterceptor
import com.kevinj1008.remoteclient.services.SearchImageService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManager : ApiProvider {

    private val searchImageService: SearchImageService

    override fun searchImageService(): SearchImageService = searchImageService

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
        private const val TIMEOUT = 10L
    }

    init {
        val interceptor = KeyInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        searchImageService = retrofit.create(SearchImageService::class.java)
    }
}