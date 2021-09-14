package com.kevinj1008.base.base

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected fun <T : Any> MutableLiveData<T>.setValueWithSync(value: T?) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            this.value = value
        } else {
            this.postValue(value)
        }
    }
}