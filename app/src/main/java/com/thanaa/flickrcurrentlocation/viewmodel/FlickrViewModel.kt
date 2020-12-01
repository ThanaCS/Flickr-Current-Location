package com.thanaa.flickrcurrentlocation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.flickrcurrentlocation.api.FlickrApi
import com.thanaa.flickrcurrentlocation.api.PhotoInterceptor
import com.thanaa.flickrcurrentlocation.model.Location
import com.thanaa.flickrcurrentlocation.model.Photo
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.flickr.com/"
class FlickrViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val locationLiveData: MutableLiveData<Location> = MutableLiveData()
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
        Log.d("FOO", "${photosLiveData.value}")
    }

    fun getGeoLocation(id: String) = viewModelScope.launch {
        val response = retrofit.getGeoLocation(id)
        if (response.isSuccessful)
            locationLiveData.postValue(response.body()?.photo?.location)
    }
}