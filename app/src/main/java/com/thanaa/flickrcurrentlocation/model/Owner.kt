package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    val iconfarm: Int,
    val iconserver: String,
    val location: String,
    val nsid: String,
    val path_alias: String,
    val realname: String,
    val username: String
) : Parcelable