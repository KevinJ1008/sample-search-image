package com.kevinj1008.base.interfaces

import com.kevinj1008.base.utils.ExceptionStatus

interface ErrorCallback {
    fun showCustomErrorView(message: String?, exceptionStatus: ExceptionStatus? = null)
}