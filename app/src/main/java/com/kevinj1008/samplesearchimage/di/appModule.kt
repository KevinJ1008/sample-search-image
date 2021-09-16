package com.kevinj1008.samplesearchimage.di

import com.kevinj1008.remoteclient.ApiManager
import com.kevinj1008.remoteclient.ApiProvider
import com.kevinj1008.remoteclient.client.ApiClient
import com.kevinj1008.remoteclient.client.SearchImageApiClient
import com.kevinj1008.remoteclient.datasource.RemoteDataSource
import com.kevinj1008.remoteclient.datasource.SearchImageRemoteDataSource
import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import com.kevinj1008.samplesearchimage.repository.SearchImageRepository
import com.kevinj1008.samplesearchimage.repository.SearchImageRepositoryImpl
import com.kevinj1008.samplesearchimage.usecase.SearchImageUseCase
import com.kevinj1008.samplesearchimage.usecase.SearchImageUseCaseImpl
import com.kevinj1008.samplesearchimage.viewmodel.SearchImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Response

val sourceModule = module {
    single<ApiProvider> { ApiManager() }
}

val apiClientModule = module {
    factory<ApiClient<Response<SearchImagePOJO>>> { SearchImageApiClient(get<ApiProvider>().searchImageService()) }
}

val remoteDataSourceModule = module {
    factory<RemoteDataSource<Response<SearchImagePOJO>>> { SearchImageRemoteDataSource(get()) }
}

val repositoryModule = module {
    factory<SearchImageRepository> { SearchImageRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory<SearchImageUseCase> { SearchImageUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { SearchImageViewModel(get()) }
}