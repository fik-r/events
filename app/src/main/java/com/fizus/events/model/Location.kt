package com.fizus.events.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val longitude: String,
    val latitude: String,
    val text: String
) : Parcelable