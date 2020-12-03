package com.thanaa.flickrcurrentlocation.api

import com.thanaa.flickrcurrentlocation.di.DaggerApiComponent
import com.thanaa.flickrcurrentlocation.model.FlickrResponse
import com.thanaa.flickrcurrentlocation.model.LocationResponse
import retrofit2.Response
import javax.inject.Inject

class FlickrService {

    @Inject
    lateinit var api: FlickrApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun fetchPhotosRequest(lat: String, lon: String): Response<FlickrResponse> {
        return api.searchPhotos(lat, lon)
    }

    suspend fun fetchGeoLocation(id: String): Response<LocationResponse> {
        return api.getGeoLocation(id)
    }
}