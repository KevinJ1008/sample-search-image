package com.kevinj1008.base.base

import androidx.fragment.app.FragmentActivity
import com.kevinj1008.base.interfaces.ErrorCallback
import com.kevinj1008.base.utils.ExceptionStatus
import com.kevinj1008.base.utils.Result
import com.kevinj1008.base.interfaces.BaseContract

abstract class BaseActivity : FragmentActivity(), BaseContract, ErrorCallback {

    override fun handleError(error: Result.Error, callback: ErrorCallback?) {
        //Haven't defined, could define base behavior if need
    }

    override fun showCustomErrorView(message: String?, exceptionStatus: ExceptionStatus?) {
        //interface for descendant to override to handle some customize view behavior
    }
}