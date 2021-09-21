package com.kevinj1008.localclient

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kevinj1008.localclient.dao.SearchHistoryDao
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO

@Database(entities = [SearchHistoryPOJO::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}