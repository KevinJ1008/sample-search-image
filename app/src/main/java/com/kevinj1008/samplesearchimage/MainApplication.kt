package com.kevinj1008.samplesearchimage

import android.app.Application
import com.kevinj1008.samplesearchimage.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MainApplication)
            modules(listOf(sourceModule, apiClientModule, localClientModule, remoteDataSourceModule,
                localDataSourceModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}