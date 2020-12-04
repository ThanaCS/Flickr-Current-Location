package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Visibility(
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int
) : Parcelable