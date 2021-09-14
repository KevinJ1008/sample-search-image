package com.kevinj1008.remoteclient.interceptor

import com.kevinj1008.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class KeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequestBuilder = originRequest.newBuilder()
        val originUrl = originRequest.url()

        if (BuildConfig.PIXABAY_KEY.isNotEmpty()) {
            originUrl.newBuilder().addQueryParameter(KEY, BuildConfig.PIXABAY_KEY)
        }

        val newRequest = newRequestBuilder.url(originUrl).build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val KEY = "key"
    }
}