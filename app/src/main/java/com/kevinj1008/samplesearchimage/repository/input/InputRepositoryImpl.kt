package com.kevinj1008.samplesearchimage.repository.input

import com.kevinj1008.base.utils.APIException
import com.kevinj1008.base.utils.CustomIOException
import com.kevinj1008.base.utils.ExceptionStatus
import com.kevinj1008.base.utils.Result
import com.kevinj1008.localclient.datasource.LocalDataSource
import com.kevinj1008.localclient.pojo.SearchHistoryPOJO
import com.kevinj1008.remoteclient.datasource.RemoteDataSource
import com.kevinj1008.remoteclient.pojo.DisplayModePOJO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class InputRepositoryImpl(
    private val remoteDataSource: RemoteDataSource<Response<DisplayModePOJO>>,
    private val localDataSource: LocalDataSource<List<SearchHistoryPOJO>>
) : InputRepository {
    override suspend fun getDisplayMode(): Result<DisplayModePOJO> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = remoteDataSource.getData()
            handleDisplayModeResult(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getSearchHistory(): Result<List<SearchHistoryPOJO>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = localDataSource.getData()
            result?.run {
                Result.Success(this)
            } ?: Result.Error(CustomIOException(ExceptionStatus.UNKNOWN_ERROR))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveSearchHistory(newSearchHistoryPOJO: SearchHistoryPOJO) = withContext(Dispatchers.IO) {
        localDataSource.insert(newSearchHistoryPOJO)
    }

    private fun handleDisplayModeResult(response: Response<DisplayModePOJO>?): Result<DisplayModePOJO> {
        return if (response != null) {
            if (response.isSuccessful) {
                response.body()?.run {
                    Result.Success(this)
                } ?: Result.Error(APIException(ExceptionStatus.NO_DATA_ERROR))
            } else {
                Result.Error(APIException(
                    ExceptionStatus.CUSTOM_ERROR(response.errorBody()?.string()),
                    response.code()
                ))
            }
        } else {
            Result.Error(APIException(ExceptionStatus.UNKNOWN_ERROR))
        }
    }
}