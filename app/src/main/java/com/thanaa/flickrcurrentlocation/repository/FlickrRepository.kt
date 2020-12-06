package com.thanaa.flickrcurrentlocation.repository

import com.thanaa.flickrcurrentlocation.api.FlickrService
import com.thanaa.flickrcurrentlocation.di.DaggerApiComponent
import com.thanaa.flickrcurrentlocation.model.FlickrResponse
import com.thanaa.flickrcurrentlocation.model.LocationResponse
import com.thanaa.flickrcurrentlocation.model.PhotoInfoResponse
import retrofit2.Response

class FlickrRepository {
    var flickrService = FlickrService()

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getPhotos(lat: String, lon: String): Response<FlickrResponse> {
        return flickrService.getPhotos(lat, lon)
    }

    suspend fun getGeoLocation(id: String): Response<LocationResponse> {
        return flickrService.getGeoLocation(id)
    }

    suspend fun getPhotoInfo(id: String): Response<PhotoInfoResponse> {
        return flickrService.getPhotoInfo(id)
    }
}