package com.kevinj1008.localclient

import android.app.Application
import androidx.room.Room
import com.kevinj1008.localclient.dao.SearchHistoryDao

class DatabaseProvider(
    application: Application
) : AppDatabase {

    private val dataBase: Database = Room
        .databaseBuilder(application, Database::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    override fun searchHistoryDao(): SearchHistoryDao = dataBase.searchHistoryDao()

    companion object {
        private const val DATABASE_NAME = "search_history.db"
    }
}