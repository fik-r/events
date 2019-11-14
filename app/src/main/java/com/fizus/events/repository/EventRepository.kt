package com.fizus.events.repository

import android.annotation.SuppressLint
import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.fizus.events.model.Event
import com.fizus.events.utility.Config
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.IllegalStateException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.InvalidParameterException

class EventRepository(private val context: Context, private val jsonParser: JsonParser, private val gson: Gson) {

    @SuppressLint("DefaultLocale")
    fun getListEvent(type: String?): List<Event> {
        if(Config.isConnected(context)) {
            try {
                val formattedType = type?.toLowerCase()?.replace(" ", "_")
                val request = AndroidNetworking.get(Config.BASE_URL_API + "event")
                    .addQueryParameter("type", formattedType)
                    .setPriority(Priority.LOW)
                    .build()
                    .executeForString()

                val response =
                    if (request.isSuccess) request.result.toString() else request.error.errorBody.toString()
                val json = jsonParser.parse(response) as JsonObject
                val status = json.get(Config.RESPONSE_STATUS).asString
                val message = json.get(Config.RESPONSE_MESSAGE).asString
                when (status) {
                    Config.RESPONSE_STATUS_SUCCESS -> {
                        val payload = json.get(Config.RESPONSE_PAYLOAD).asJsonArray
                        return gson.fromJson(
                            payload,
                            (object : TypeToken<List<Event>>() {}.type)
                        ) as List<Event>
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

//    fun createEvent(
//        thumbnail: File,
//        photos: List<File>,
//        title: String,
//        category: String,
//        description: String,
//        startsOn: String,
//        endsOn: String,
//        locationLatitude: String?,
//        locationLongitude: String?,
//        locationText: String,
//        privacy: String,
//        ticketType: String,
//        ticketPrice: String?,
//        ticketStock: String?
//        ): ANRequest<*> {
//        return AndroidNetworking.upload(Config.BASE_URL_API + "event/create")
//            .addMultipartFile("thumbnail", thumbnail)
//            .addMultipartFileList("photos", photos)
//            .addMultipartParameter("title", title)
//            .addMultipartParameter("category", category)
//            .addMultipartParameter("description", description)
//            .addMultipartParameter("starts_on", startsOn)
//            .addMultipartParameter("ends_on", endsOn)
//            .addMultipartParameter("location_longitude", locationLongitude)
//            .addMultipartParameter("location_latitude", locationLatitude)
//            .addMultipartParameter("location_text", locationText)
//            .addMultipartParameter("privacy", privacy)
//            .addMultipartParameter("ticket_type", ticketType)
//            .addMultipartParameter("ticket_price", ticketPrice)
//            .addMultipartParameter("ticket_stock", ticketStock)
//            .setPriority(Priority.IMMEDIATE)
//            .build()
//    }
}