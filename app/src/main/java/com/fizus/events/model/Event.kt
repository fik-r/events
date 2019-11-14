package com.fizus.events.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val _id: String,
    val thumbnail: String,
    val title: String,
    val category: String,
    val description: String,
    val by: String,
    @SerializedName("starts_on")
    val startsOn: String,
    @SerializedName("ends_on")
    val endsOn: String,
    val location: Location,
    val privacy: String,
    val ticket: Ticket,
    val photos: Array<String>,
    val status: String
) : Parcelable