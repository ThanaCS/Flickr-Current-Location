package com.thanaa.flickrcurrentlocation.api

import com.thanaa.flickrcurrentlocation.model.FlickrResponse
import com.thanaa.flickrcurrentlocation.model.LocationResponse
import com.thanaa.flickrcurrentlocation.model.PhotoInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search")
    suspend fun getPhotos(
        @Query("lat") lat: String? = null,
        @Query("lon") lon: String? = null
    ): Response<FlickrResponse>

    @GET("services/rest?method=flickr.photos.geo.getLocation")
    suspend fun getGeoLocation(@Query("photo_id") id: String? = null): Response<LocationResponse>

    @GET("services/rest?method=flickr.photos.getInfo")
    suspend fun getPhotoInfo(@Query("photo_id") id: String? = null): Response<PhotoInfoResponse>
}