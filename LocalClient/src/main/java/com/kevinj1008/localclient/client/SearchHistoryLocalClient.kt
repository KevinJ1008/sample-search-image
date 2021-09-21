package com.kevinj1008.localclient.client

import com.kevinj1008.localclient.dao.SearchHistoryDao
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO

class SearchHistoryLocalClient(
    private val searchHistoryDao: SearchHistoryDao
) : LocalClient<List<SearchHistoryPOJO>> {

    override suspend fun getData(vararg args: Any): List<SearchHistoryPOJO> {
        return searchHistoryDao.getSearchHistories()
    }

    override suspend fun insert(vararg args: Any) {
        if (args.isEmpty()) {
            return
        }
        val searchHistoryPOJO = args.getOrNull(0)
        if (searchHistoryPOJO is SearchHistoryPOJO) {
            searchHistoryDao.saveSearchHistory(newSearchHistory = searchHistoryPOJO)
        }
    }
}