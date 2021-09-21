package com.kevinj1008.localclient.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_history")
data class SearchHistoryPOJO(
    @PrimaryKey
    @ColumnInfo(name = "keyword")
    @SerializedName("keyword") val keyword: String,

    @ColumnInfo(name = "created_time")
    @SerializedName("created_time") val createdTime: Long = System.currentTimeMillis()
)
