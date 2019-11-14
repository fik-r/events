package com.fizus.events.fragment.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.fizus.events.R
import com.fizus.events.repository.AuthRepository
import com.fizus.events.utility.Config
import com.fizus.events.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    private val loginSuccess = MutableLiveData<String>()

    fun isLoginSuccess(): LiveData<String> = loginSuccess

    suspend fun login(email: String, password: String) {
        var isFormEmpty = false
        if (email.isEmpty()) {
            error.postValue(mapOf(R.id.et_email to "You need to fill email"))
            isFormEmpty = true
        }
        if (password.isEmpty()) {
            error.postValue(mapOf(R.id.et_password to "You need to fill password"))
            isFormEmpty = true
        }
        if (isFormEmpty) return

        try {
            loading.value = true
            withContext(Dispatchers.IO) {
                return@withContext authRepository.login(email, password)
            }.let {
                Log.d("", it)
                loginSuccess.value = it
            }
        } catch (e: Exception){
            error.value = mapOf(0 to e.message.toString())
            e.printStackTrace()
        } finally {
            loading.value = false
        }
    }

}