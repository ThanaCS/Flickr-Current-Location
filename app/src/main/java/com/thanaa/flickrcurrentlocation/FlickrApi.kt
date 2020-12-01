package com.thanaa.flickrcurrentlocation

import com.thanaa.flickrcurrentlocation.model.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("lat") lat: String? = null,
                             @Query("lon") lon: String? = null): Response<FlickrResponse>

    @GET("services/rest?method=flickr.photos.geo.getLocation")
    suspend fun getGeoLocation(@Query("photo_id") id: String? = null): Response<LocationResponse>

}