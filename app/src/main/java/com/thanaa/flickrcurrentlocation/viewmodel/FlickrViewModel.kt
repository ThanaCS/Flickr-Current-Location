package com.thanaa.flickrcurrentlocation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thanaa.flickrcurrentlocation.di.DaggerApiComponent
import com.thanaa.flickrcurrentlocation.model.Location
import com.thanaa.flickrcurrentlocation.model.Photo
import com.thanaa.flickrcurrentlocation.model.PhotoInfo
import com.thanaa.flickrcurrentlocation.repository.FlickrRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FlickrViewModel : ViewModel() {
    @Inject
    lateinit var repository: FlickrRepository


    init {
        DaggerApiComponent.create().inject(this)
    }

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val locationLiveData: MutableLiveData<Location> = MutableLiveData()
    val photoInfoLiveData: MutableLiveData<PhotoInfo> = MutableLiveData()

    // get photos near current location
    fun getPhotos(lat: String, lon: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getPhotos(lat, lon)
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
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getGeoLocation(id)
                if (response.isSuccessful)
                    locationLiveData.postValue(response.body()?.photo?.location)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
    //get photo info of the photo
    fun getPhotoInfo(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getPhotoInfo(id)
                if (response.isSuccessful)
                    photoInfoLiveData.postValue(response.body()?.photo)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}