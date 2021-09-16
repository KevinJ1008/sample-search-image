package com.kevinj1008.remoteclient.client

/**
 * We wrap api caller into this class, once we need to change another lib in the future, we just
 * modified the class we implemented
 */
interface ApiClient<T> {
    suspend fun requestData(vararg args: Any): T?
}