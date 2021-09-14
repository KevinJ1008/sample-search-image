package com.kevinj1008.remoteclient.datasource

interface RemoteDataSource<T> {
    suspend fun getData(vararg args: Any): T?
}