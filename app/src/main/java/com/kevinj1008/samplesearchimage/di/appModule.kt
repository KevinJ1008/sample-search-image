package com.kevinj1008.samplesearchimage.di

import com.kevinj1008.remoteclient.ApiManager
import com.kevinj1008.remoteclient.ApiProvider
import org.koin.dsl.module

val sourceModule = module {
    single<ApiProvider> { ApiManager() }
}