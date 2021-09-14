package com.kevinj1008.remoteclient.client

interface ApiClient<T> {
    suspend fun requestData(vararg args: Any): T?
}