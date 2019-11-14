package com.fizus.events.fragment.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.fizus.events.R
import com.fizus.events.base.BaseViewModel
import com.fizus.events.repository.AuthRepository
import com.fizus.events.utility.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class RegisterViewModel(private val authRepository: AuthRepository) : BaseViewModel() {
    private val registerSuccess = MutableLiveData<String>()

    fun isRegisterSuccess(): LiveData<String> {
        return registerSuccess
    }

    suspend fun register(fullName: String, email: String, password: String) {
        var isFormEmpty = false
        if(fullName.isEmpty()){
            error.postValue(mapOf(R.id.et_name to "You need to fill name"))
            isFormEmpty = true
        }
        if (email.isEmpty()) {
            error.postValue(mapOf(R.id.et_email to "You need to fill email"))
            isFormEmpty = true
        }
        if (password.isEmpty()) {
            error.postValue(mapOf(R.id.et_password to "You need to fill password"))
            isFormEmpty = true
        }
        if (isFormEmpty) return

        //coroutine
        try {
            loading.value = true
            withContext(Dispatchers.IO){
                return@withContext authRepository.register(fullName, email, password)
            }.let {
                registerSuccess.value = it
            }
        } catch (e: Exception){
            error.value = mapOf(0 to e.message.toString())
            e.printStackTrace()
        } finally {
            loading.value = false
        }
    }
}