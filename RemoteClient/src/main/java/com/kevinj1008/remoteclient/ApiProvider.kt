package com.kevinj1008.remoteclient

import com.kevinj1008.remoteclient.services.SearchImageService

interface ApiProvider {
    fun searchImageService(): SearchImageService
}