package com.thanaa.flickrcurrentlocation.api
import com.thanaa.flickrcurrentlocation.di.DaggerApiComponent
import com.thanaa.flickrcurrentlocation.model.FlickrResponse
import com.thanaa.flickrcurrentlocation.model.LocationResponse
import com.thanaa.flickrcurrentlocation.model.PhotoInfoResponse
import retrofit2.Response
import javax.inject.Inject

class FlickrService {

    @Inject
    lateinit var api: FlickrApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getPhotos(lat: String, lon: String): Response<FlickrResponse> {
        return api.getPhotos(lat, lon)
    }

    suspend fun getGeoLocation(id: String): Response<LocationResponse> {
        return api.getGeoLocation(id)
    }

    suspend fun getPhotoInfo(id: String): Response<PhotoInfoResponse> {
        return api.getPhotoInfo(id)
    }
}