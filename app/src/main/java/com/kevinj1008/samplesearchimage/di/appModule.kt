package com.kevinj1008.samplesearchimage.di

import com.kevinj1008.localclient.DatabaseProvider
import com.kevinj1008.localclient.client.LocalClient
import com.kevinj1008.localclient.client.SearchHistoryLocalClient
import com.kevinj1008.localclient.datasource.LocalDataSource
import com.kevinj1008.localclient.datasource.SearchHistoryLocalDataSource
import com.kevinj1008.localclient.AppDatabase
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO
import com.kevinj1008.remoteclient.ApiManager
import com.kevinj1008.remoteclient.ApiProvider
import com.kevinj1008.remoteclient.client.ApiClient
import com.kevinj1008.remoteclient.client.DisplayModeApiClient
import com.kevinj1008.remoteclient.client.SearchImageApiClient
import com.kevinj1008.remoteclient.datasource.DisplayModeRemoteDataSource
import com.kevinj1008.remoteclient.datasource.RemoteDataSource
import com.kevinj1008.remoteclient.datasource.SearchImageRemoteDataSource
import com.kevinj1008.remoteclient.pojo.DisplayModePOJO
import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import com.kevinj1008.samplesearchimage.repository.input.InputRepository
import com.kevinj1008.samplesearchimage.repository.input.InputRepositoryImpl
import com.kevinj1008.samplesearchimage.repository.searchimage.SearchImageRepository
import com.kevinj1008.samplesearchimage.repository.searchimage.SearchImageRepositoryImpl
import com.kevinj1008.samplesearchimage.usecase.input.InputUseCase
import com.kevinj1008.samplesearchimage.usecase.input.InputUseCaseImpl
import com.kevinj1008.samplesearchimage.usecase.searchimage.SearchImageUseCase
import com.kevinj1008.samplesearchimage.usecase.searchimage.SearchImageUseCaseImpl
import com.kevinj1008.samplesearchimage.viewmodel.InputViewModel
import com.kevinj1008.samplesearchimage.viewmodel.SearchImageViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Response

val sourceModule = module {
    single<ApiProvider> { ApiManager() }
    single<AppDatabase> { DatabaseProvider(androidApplication()) }
}

val apiClientModule = module {
    factory<ApiClient<Response<SearchImagePOJO>>>(named(SEARCH_IMAGE)) {
        SearchImageApiClient(get<ApiProvider>().searchImageService())
    }
    factory<ApiClient<Response<DisplayModePOJO>>>(named(DISPLAY_MODE)) {
        DisplayModeApiClient()
    }
}

val localClientModule = module {
    factory<LocalClient<List<SearchHistoryPOJO>>>(named(SEARCH_HISTORY)) {
        SearchHistoryLocalClient(get<AppDatabase>().searchHistoryDao())
    }
}

val remoteDataSourceModule = module {
    factory<RemoteDataSource<Response<SearchImagePOJO>>>(named(SEARCH_IMAGE)) {
        SearchImageRemoteDataSource(get(named(SEARCH_IMAGE)))
    }
    factory<RemoteDataSource<Response<DisplayModePOJO>>>(named(DISPLAY_MODE)) {
        DisplayModeRemoteDataSource(get(named(DISPLAY_MODE)))
    }
}

val localDataSourceModule = module {
    factory<LocalDataSource<List<SearchHistoryPOJO>>>(named(SEARCH_HISTORY)) {
        SearchHistoryLocalDataSource(get(named(SEARCH_HISTORY)))
    }
}

val repositoryModule = module {
    factory<SearchImageRepository> {
        SearchImageRepositoryImpl(get(named(SEARCH_IMAGE)))
    }
    factory<InputRepository> {
        InputRepositoryImpl(get(named(DISPLAY_MODE)), get(named(SEARCH_HISTORY)))
    }
}

val useCaseModule = module {
    factory<SearchImageUseCase> { SearchImageUseCaseImpl(get()) }
    factory<InputUseCase> { InputUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { SearchImageViewModel(get()) }
    viewModel { InputViewModel(get()) }
}