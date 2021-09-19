package com.kevinj1008.samplesearchimage.repository.searchimage

import com.kevinj1008.base.utils.APIException
import com.kevinj1008.base.utils.ExceptionStatus
import com.kevinj1008.base.utils.Result
import com.kevinj1008.remoteclient.datasource.RemoteDataSource
import com.kevinj1008.remoteclient.model.ApiModel
import com.kevinj1008.remoteclient.pojo.ImageResultPOJO
import com.kevinj1008.remoteclient.pojo.SearchImagePOJO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class SearchImageRepositoryImpl(
    private val remoteDataSource: RemoteDataSource<Response<SearchImagePOJO>>
    ) : SearchImageRepository {

    override suspend fun searchImages(key: ApiModel): Result<List<ImageResultPOJO>>
    = withContext(Dispatchers.IO) {
        val isFetching = key is ApiModel.FetchPage
        return@withContext try {
            val response = remoteDataSource.getData(key)
            handleData(response, isFetching)
        } catch (e: Exception) {
            Result.Error(exception = e, isFetching = isFetching)
        }
    }

    private fun handleData(response: Response<SearchImagePOJO>?, isFetching: Boolean = false): Result<List<ImageResultPOJO>> {
        return if (response != null) {
            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                if (data.isEmpty()) {
                    Result.Error(
                        exception = APIException(ExceptionStatus.NO_DATA_ERROR),
                        isFetching = isFetching
                    )
                } else {
                    Result.Success(data)
                }
            } else {
                Result.Error(
                    exception = APIException(ExceptionStatus.CUSTOM_ERROR(response.errorBody()?.string()),
                    response.code()),
                    isFetching = isFetching
                )
            }
        } else {
            Result.Error(
                exception = APIException(ExceptionStatus.UNKNOWN_ERROR),
                isFetching = isFetching
            )
        }
    }
}