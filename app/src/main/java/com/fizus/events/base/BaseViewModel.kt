package com.fizus.events.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){

    protected val loading: MutableLiveData<Boolean> = MutableLiveData()
    protected val error: MutableLiveData<Map<Int, String>> = MutableLiveData()

    fun onLoading(): LiveData<Boolean> {
        return loading
    }

    fun onError(): LiveData<Map<Int, String>> {
        return error
    }
}