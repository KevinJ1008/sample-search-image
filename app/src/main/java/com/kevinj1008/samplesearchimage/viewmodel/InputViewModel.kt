package com.kevinj1008.samplesearchimage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kevinj1008.base.base.BaseViewModel
import com.kevinj1008.base.utils.Result
import com.kevinj1008.base.utils.toLiveData
import com.kevinj1008.samplesearchimage.entity.DisplayMode
import com.kevinj1008.samplesearchimage.usecase.input.InputUseCase
import kotlinx.coroutines.launch

class InputViewModel(
    private val useCase: InputUseCase
) : BaseViewModel() {
    private val _displayMode = MutableLiveData<DisplayMode>()
    val displayMode = _displayMode.toLiveData()

    fun getDisplayMode() {
        viewModelScope.launch {
            val result = useCase.getDisplayMode()
            if (result is Result.Success) {
                _displayMode.setValueWithSync(result.data)
            } else {
                //We won't have error case in here base on our design
            }
        }
    }
}