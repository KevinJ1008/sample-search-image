package com.kevinj1008.samplesearchimage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinj1008.base.base.BaseViewModel
import com.kevinj1008.base.utils.Event
import com.kevinj1008.base.utils.Result
import com.kevinj1008.base.utils.toLiveData
import com.kevinj1008.samplesearchimage.entity.ImageEntity
import com.kevinj1008.samplesearchimage.usecase.searchimage.SearchImageUseCase
import kotlinx.coroutines.launch

class SearchImageViewModel(
    private val searchImageUseCase: SearchImageUseCase
) : BaseViewModel() {
    private val _imageList = MutableLiveData<List<ImageEntity>>()
    val imageList = _imageList.toLiveData()

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading = _isLoading.toLiveData()

    private val _loadingError = MutableLiveData<Event<Result.Error>>()
    val loadingError = _loadingError.toLiveData()

    fun searchImage(keyword: String) {
        _isLoading.setValueWithSync(Event(content = true))
        viewModelScope.launch {
            val result = searchImageUseCase.getImages(keyword = keyword)
            handleResult(result)
        }
    }

    fun fetchNextPage(keyword: String, page: String) {
        _isLoading.setValueWithSync(Event(content = true))
        viewModelScope.launch {
            val result = searchImageUseCase.fetchNextPage(keyword = keyword, page = page)
            handleResult(result)
        }
    }

    private fun handleResult(result: Result<List<ImageEntity>>) {
        _isLoading.setValueWithSync(Event(content = false))
        when (result) {
            is Result.Success -> {
                _imageList.setValueWithSync(result.data)
            }

            is Result.Error -> {
                _loadingError.setValueWithSync(Event(content = result))
            }
        }
    }
}