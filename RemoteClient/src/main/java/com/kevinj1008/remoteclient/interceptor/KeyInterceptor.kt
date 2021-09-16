package com.kevinj1008.remoteclient.interceptor

import com.kevinj1008.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequestBuilder = originRequest.newBuilder()
        var originUrl = originRequest.url()

        if (BuildConfig.PIXABAY_KEY.isNotEmpty()) {
            originUrl = originUrl
                .newBuilder()
                .addQueryParameter(KEY, BuildConfig.PIXABAY_KEY)
                .build()
        }

        val newRequest = newRequestBuilder.url(originUrl).build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val KEY = "key"
    }
}