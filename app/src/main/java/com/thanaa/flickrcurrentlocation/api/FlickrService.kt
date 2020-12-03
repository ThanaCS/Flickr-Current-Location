package com.thanaa.flickrcurrentlocation.api

import com.thanaa.flickrcurrentlocation.model.FlickrResponse
import com.thanaa.flickrcurrentlocation.model.LocationResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.flickr.com/"

class FlickrService {
    private val client = OkHttpClient.Builder()
        .addInterceptor(PhotoInterceptor())
        .build()
    private val api: FlickrApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)// Interceptor
        .build().create(FlickrApi::class.java)

    suspend fun fetchPhotosRequest(lat: String, lon: String): Response<FlickrResponse> {
        return api.searchPhotos(lat, lon)
    }

    suspend fun fetchGeoLocation(id: String): Response<LocationResponse> {
        return api.getGeoLocation(id)
    }
}