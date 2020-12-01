package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationResponse(
        val photo: PhotoLocation,
        val stat: String
) : Parcelable
