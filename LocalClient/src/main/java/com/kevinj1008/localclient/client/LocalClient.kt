package com.kevinj1008.localclient.client

interface LocalClient<T> {
    suspend fun getData(vararg args: Any): T?
    suspend fun insert(vararg args: Any)
}