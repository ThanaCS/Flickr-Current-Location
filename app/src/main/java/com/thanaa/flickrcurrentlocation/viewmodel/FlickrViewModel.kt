package com.thanaa.flickrcurrentlocation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.flickrcurrentlocation.api.FlickrService
import com.thanaa.flickrcurrentlocation.di.DaggerApiComponent
import com.thanaa.flickrcurrentlocation.model.Location
import com.thanaa.flickrcurrentlocation.model.Photo
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlickrViewModel : ViewModel() {

    @Inject
    lateinit var flickerService: FlickrService

    init {
        DaggerApiComponent.create().inject(this)
    }

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val locationLiveData: MutableLiveData<Location> = MutableLiveData()


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