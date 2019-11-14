package com.fizus.events.repository

import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.fizus.events.utility.Config
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.InvalidParameterException

class AuthRepository(private val context: Context, private val jsonParser: JsonParser) {
    fun login(email: String, password: String): String {
        if (Config.isConnected(context)) {
            try {
                val request = AndroidNetworking.post(Config.BASE_URL_API + "auth/login")
                    .addBodyParameter("email", email)
                    .addBodyParameter("password", password)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .executeForString()

                val response =
                    if (request.isSuccess) request.result.toString() else request.error.errorBody.toString()

                val json = jsonParser.parse(response) as JsonObject
                val status = json.get(Config.RESPONSE_STATUS).asString
                val message = json.get(Config.RESPONSE_MESSAGE).asString
                when (status) {
                    Config.RESPONSE_STATUS_SUCCESS -> {
                        val payload = json.get(Config.RESPONSE_PAYLOAD).asJsonObject
                        return payload.get("token").asString
                    }
                    Config.RESPONSE_STATUS_ERROR -> {
                        throw InvalidParameterException(message)
                    }
                    else -> {
                        throw IllegalArgumentException("Unknown state")
                    }
                }
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Timeout, try again with better connection")
            } catch (e: IOException) {
                e.printStackTrace()
                throw IOException("Oopss.. something wrong with server")
            }
        } else {
            throw UnknownHostException("No internet connection")
        }
    }

    fun register(fullName: String, email: String, password: String): String {
        if (Config.isConnected(context)) {
            try {
                val request = AndroidNetworking.post(Config.BASE_URL_API + "auth/register")
                    .addBodyParameter("fullname", fullName)
                    .addBodyParameter("email", email)
                    .addBodyParameter("password", password)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .executeForString()

                val response =
                    if (request.isSuccess) request.result.toString() else request.error.errorBody.toString()

                val json = jsonParser.parse(response) as JsonObject
                val status = json.get(Config.RESPONSE_STATUS).asString
                val message = json.get(Config.RESPONSE_MESSAGE).asString
                when (status) {
                    Config.RESPONSE_STATUS_SUCCESS -> {
                        val payload = json.get(Config.RESPONSE_PAYLOAD).asJsonObject
                        return payload.get("token").asString
                    }
                    Config.RESPONSE_STATUS_ERROR -> {
                        throw InvalidParameterException(message)
                    }
                    else -> {
                        throw IllegalArgumentException("Unknown status")
                    }
                }
            } catch (e: SocketTimeoutException) {
                throw SocketTimeoutException("Timeout, try again with better connection")
            } catch (e: IOException) {
                e.printStackTrace()
                throw IOException("Oopss.. something wrong with server")
            }
        } else {
            throw UnknownHostException("No internet connection")
        }
    }
}