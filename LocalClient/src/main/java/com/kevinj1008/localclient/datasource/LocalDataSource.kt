package com.kevinj1008.localclient.datasource

interface LocalDataSource<T> {
    suspend fun getData(vararg args: Any): T?
    suspend fun insert(vararg args: Any)
}