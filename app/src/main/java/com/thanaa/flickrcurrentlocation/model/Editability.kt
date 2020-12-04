package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Editability(
    val canaddmeta: Int,
    val cancomment: Int
) : Parcelable