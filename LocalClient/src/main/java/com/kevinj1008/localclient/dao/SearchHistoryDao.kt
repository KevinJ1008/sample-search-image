package com.kevinj1008.localclient.dao

import androidx.room.*
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM `search_history`")
    suspend fun getSearchHistories(): List<SearchHistoryPOJO>

    @Delete
    suspend fun deleteSearchHistory(searchHistoryPOJO: SearchHistoryPOJO)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSearchHistory(newSearchHistory: SearchHistoryPOJO)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewSearchHistory(newSearchHistory: SearchHistoryPOJO): Long

    @Transaction
    suspend fun saveSearchHistory(newSearchHistory: SearchHistoryPOJO) {
        val list = getSearchHistories()
        if (list.size == 10) {
            //delete the oldest and insert the newest
            if (!upsert(newSearchHistory)) {
                list.minByOrNull { it.createdTime }?.apply {
                    deleteSearchHistory(this)
                }
            }
        } else {
            upsert(newSearchHistory)
        }
    }

    @Transaction
    suspend fun upsert(newSearchHistory: SearchHistoryPOJO): Boolean {
        if (-1L == insertNewSearchHistory(newSearchHistory)) {
            updateSearchHistory(newSearchHistory)
            return true
        }
        return false
    }
}