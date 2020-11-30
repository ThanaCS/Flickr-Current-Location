package com.thanaa.flickrcurrentlocation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickrViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()

    private val client = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor())
            .build()

    private val retrofit: FlickrApi = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)// Interceptor
            .build().create(FlickrApi::class.java)

    fun getPhotos(lat: String, lon: String) = viewModelScope.launch {
        val response = retrofit.searchPhotos(lat, lon)
        if (response.isSuccessful)
            photosLiveData.postValue(response.body()?.photos?.photo)
    }
}