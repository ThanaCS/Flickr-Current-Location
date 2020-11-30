package com.thanaa.flickrcurrentlocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL = "https://api.flickr.com/"
class FlickrViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()

    private val client = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()

    private val retrofit: FlickrApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)// Interceptor
            .build().create(FlickrApi::class.java)

    fun getPhotos(lat: String, lon: String) = viewModelScope.launch {
        val response = retrofit.searchPhotos(lat, lon)
        if (response.isSuccessful)
            photosLiveData.postValue(response.body()?.photos?.photo)
    }

    fun getGeoLocation(api: String, id: String) = viewModelScope.launch {
        val response = retrofit.getGeoLocation(api, id)
        if (response.isSuccessful)
            photosLiveData.postValue(response.body()?.photos?.photo)
    }
}