package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrResponse(
        val photos: Photos,
        val stat: String) : Parcelable