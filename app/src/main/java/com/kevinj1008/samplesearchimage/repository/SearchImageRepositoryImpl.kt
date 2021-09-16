package com.kevinj1008.samplesearchimage.repository

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

    override suspend fun getSearchImage(keyword: ApiModel.SearchImage): Result<List<ImageResultPOJO>>
    = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = remoteDataSource.getData(keyword)
            handleData(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun handleData(response: Response<SearchImagePOJO>?): Result<List<ImageResultPOJO>> {
        return if (response != null) {
            if (response.isSuccessful) {
                val data = response.body()?.results ?: emptyList()
                if (data.isEmpty()) {
                    Result.Error(APIException(ExceptionStatus.NO_DATA_ERROR))
                } else {
                    Result.Success(data)
                }
            } else {
                Result.Error(APIException(ExceptionStatus.CUSTOM_ERROR(response.errorBody()?.string()),
                    response.code()))
            }
        } else {
            Result.Error(APIException(ExceptionStatus.UNKNOWN_ERROR))
        }
    }
}