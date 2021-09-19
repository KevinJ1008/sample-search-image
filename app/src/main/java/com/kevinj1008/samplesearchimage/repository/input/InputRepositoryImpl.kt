package com.kevinj1008.samplesearchimage.repository.input

import com.kevinj1008.base.utils.APIException
import com.kevinj1008.base.utils.ExceptionStatus
import com.kevinj1008.base.utils.Result
import com.kevinj1008.remoteclient.datasource.RemoteDataSource
import com.kevinj1008.remoteclient.pojo.DisplayModePOJO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class InputRepositoryImpl(
    private val remoteDataSource: RemoteDataSource<Response<DisplayModePOJO>>
) : InputRepository {
    override suspend fun getDisplayMode(): Result<DisplayModePOJO> = withContext(Dispatchers.IO) {
        return@withContext try {
            val result = remoteDataSource.getData()
            handleDisplayModeResult(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
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