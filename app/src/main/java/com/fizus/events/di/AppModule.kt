package com.fizus.events.di

import android.content.Context.MODE_PRIVATE
import com.fizus.events.fragment.auth.LoginViewModel
import com.fizus.events.fragment.auth.RegisterViewModel
import com.fizus.events.fragment.content.events.EventsViewModel
import com.fizus.events.repository.AuthRepository
import com.fizus.events.repository.EventRepository
import com.fizus.events.utility.Config
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { androidContext().getSharedPreferences(Config.PREF_NAME, MODE_PRIVATE) }
    single { JsonParser() }
    single { Gson() }
}

val dataModule = module {
    single { AuthRepository(get(), get()) }
    single { EventRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { EventsViewModel(get()) }
}