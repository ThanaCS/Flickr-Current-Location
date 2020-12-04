package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geoperms(
    val iscontact: Int,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int
) : Parcelable