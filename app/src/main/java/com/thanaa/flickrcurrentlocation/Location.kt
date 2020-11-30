package com.thanaa.flickrcurrentlocation

data class Location(
        val accuracy: String,
        val context: String,
        val country: Country,
        val latitude: String,
        val longitude: String,
)