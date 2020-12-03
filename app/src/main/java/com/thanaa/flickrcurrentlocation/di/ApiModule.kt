package com.thanaa.flickrcurrentlocation.di

import com.thanaa.flickrcurrentlocation.api.FlickrApi
import com.thanaa.flickrcurrentlocation.api.FlickrService
import com.thanaa.flickrcurrentlocation.api.PhotoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.flickr.com/"

@Module
class ApiModule {
    @Provides
    fun provideFlickrApi(): FlickrApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()
        val api: FlickrApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)// Interceptor
            .build().create(FlickrApi::class.java)
        return api
    }

    @Provides
    fun provideFlickrService(): FlickrService {

        return FlickrService()
    }
}