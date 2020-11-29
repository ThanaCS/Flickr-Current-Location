package com.thanaa.flickrcurrentlocation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest?method=flickr.photos.search")
    fun searchPhotos(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<FlickrResponse>


}