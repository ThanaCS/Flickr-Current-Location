package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dates(
    val lastupdate: String,
    val posted: String,
    val taken: String,
    val takengranularity: String,
    val takenunknown: String
) : Parcelable