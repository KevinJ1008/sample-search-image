package com.kevinj1008.localclient

import com.kevinj1008.localclient.dao.SearchHistoryDao

interface AppDatabase {
    fun searchHistoryDao(): SearchHistoryDao
}