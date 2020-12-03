package com.thanaa.flickrcurrentlocation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.flickrcurrentlocation.api.FlickrApi
import com.thanaa.flickrcurrentlocation.api.FlickrService
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
    val flickerService = FlickrService()
    private val client = OkHttpClient.Builder()
        .addInterceptor(PhotoInterceptor())
        .build()
    private val retrofit: FlickrApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)// Interceptor
            .build().create(FlickrApi::class.java)

    // get photos near current location
    fun getPhotos(lat: String, lon: String) {
        try {
            viewModelScope.launch {
                val response = flickerService.fetchPhotosRequest(lat, lon)
                if (response.isSuccessful)
                    photosLiveData.postValue(response.body()?.photos?.photo)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    //get location of the photo
    fun getGeoLocation(id: String) {
        try {
            viewModelScope.launch {
                val response = flickerService.fetchGeoLocation(id)
                if (response.isSuccessful)
                    locationLiveData.postValue(response.body()?.photo?.location)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}