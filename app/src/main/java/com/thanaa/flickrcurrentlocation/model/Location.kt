package com.thanaa.flickrcurrentlocation.model

data class Location(
        val accuracy: String,
        val context: String,
        val country: Country,
        val latitude: String,
        val locality: Locality,
        val longitude: String,
        val region: Region)
