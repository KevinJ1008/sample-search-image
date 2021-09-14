package com.kevinj1008.base.interfaces

import com.kevinj1008.base.utils.Result

interface BaseContract {
    /**
     * @param error to determine handle what error
     * @param callback to let subclass to show what they want
     */
    fun handleError(error: Result.Error, callback: ErrorCallback? = null)
}