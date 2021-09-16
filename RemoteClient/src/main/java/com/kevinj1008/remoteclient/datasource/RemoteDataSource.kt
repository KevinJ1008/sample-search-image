package com.kevinj1008.remoteclient.datasource

/**
 * We don't need to know what request lib we use, we only want to call api and return data we want
 */
interface RemoteDataSource<T> {
    suspend fun getData(vararg args: Any): T?
}