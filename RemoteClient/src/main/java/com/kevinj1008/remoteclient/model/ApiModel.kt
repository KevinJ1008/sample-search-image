package com.kevinj1008.remoteclient.model

sealed class ApiModel {
    data class SearchImage(val keyword: String) : ApiModel()
    data class FetchPage(val keyword: String, val page: String) : ApiModel()
}