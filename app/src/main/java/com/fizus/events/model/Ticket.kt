package com.fizus.events.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(
    val type: String,
    val price: String,
    val stock: String
) : Parcelable