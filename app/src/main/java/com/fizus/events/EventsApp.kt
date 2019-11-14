package com.fizus.events

import android.app.Application
import android.content.Context
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.fizus.events.di.appModule
import com.fizus.events.di.dataModule
import com.fizus.events.di.viewModelModule
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EventsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient().newBuilder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        AndroidNetworking.initialize(this@EventsApp, okHttpClient)

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        startKoin {
            androidLogger()
            androidContext(this@EventsApp)
            modules(listOf(appModule, dataModule, viewModelModule))
        }
    }
}