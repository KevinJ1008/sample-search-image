package com.kevinj1008.localclient.datasource

import com.kevinj1008.localclient.client.LocalClient
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO

class SearchHistoryLocalDataSource(
    private val localClient: LocalClient<List<SearchHistoryPOJO>>
) : LocalDataSource<List<SearchHistoryPOJO>> {
    override suspend fun getData(vararg args: Any): List<SearchHistoryPOJO>? {
        return localClient.getData(*args)
    }

    override suspend fun insert(vararg args: Any) {
        localClient.insert(*args)
    }
}