package com.thanaa.flickrcurrentlocation

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search")
    suspend fun searchPhotos(@Query("lat") lat: String? = null,
                             @Query("lon") lon: String? = null): Response<FlickrResponse>

    @GET("flickr.photos.geo.getLocation")
    suspend fun getGeoLocation(@Query("api_key") api: String? = null,
                               @Query("photo_id") id: String? = null): Response<FlickrResponse>

}