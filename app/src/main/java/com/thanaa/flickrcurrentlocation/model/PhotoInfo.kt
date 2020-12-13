package com.thanaa.flickrcurrentlocation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoInfo(
    val comments: Comments,
    val dates: Dates,
    val dateuploaded: String,
    val description: Description,
    val editability: Editability,
    val farm: Int,
    val geoperms: Geoperms,
    val id: String,
    val isfavorite: Int,
    val license: String,
    val media: String,
    val originalformat: String,
    val originalsecret: String,
    val owner: Owner,
    val people: People,
    val safety_level: String,
    val secret: String,
    val server: String,
    val title: Title,
    val views: String,
    val visibility: Visibility
) : Parcelable