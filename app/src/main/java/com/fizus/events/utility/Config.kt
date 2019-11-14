package com.fizus.events.utility

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager


class Config {
    companion object {
        const val EVENTS_ALL = "All"
        const val EVENTS_UPCOMING = "Upcoming"
        const val EVENTS_FINISHED = "Finished"
        const val EVENTS_WAITING_LIST = "Waiting List"

        // Url
        private const val BASE_URL = "https://events-332.herokuapp.com"
        const val BASE_URL_API = "$BASE_URL/api/v1/"
        const val BASE_URL_UPLOADS = "$BASE_URL/uploads/"

        // Response
        const val RESPONSE_STATUS = "status"
        const val RESPONSE_STATUS_SUCCESS = "success"
        const val RESPONSE_STATUS_ERROR = "error"
        const val RESPONSE_MESSAGE = "message"
        const val RESPONSE_PAYLOAD = "payload"

        // Preferences
        const val PREF_NAME = "events"
        const val PREF_TOKEN = "pref_token"

        const val KEY_DETAIL_EVENT = "detail_event"

        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
        }
    }
}