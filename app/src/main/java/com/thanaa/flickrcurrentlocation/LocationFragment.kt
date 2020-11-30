package com.thanaa.flickrcurrentlocation

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private var TAG = "LocationFragment"
var lat: String = "0"
var long: String = "0"
var PERMISSION_ID: Int = 1

class LocationFragment : Fragment(R.layout.fragment_location) {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getNewLocation()
        getLastLocation()
        getNewLocation()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoList: RecyclerView = requireView().findViewById(R.id.photo_list)
        val client = OkHttpClient.Builder()
                .addInterceptor(PhotoInterceptor())
                .build()

        val retrofit: FlickrApi = Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)// Interceptor
                .build().create(FlickrApi::class.java)



        GlobalScope.launch {

            val response = retrofit.searchPhotos(lat, long)

            if (response.isSuccessful) {
                if (response.body() != null) {
                    val data = response.body()?.photos?.photo
                    withContext(Dispatchers.Main) {
                        Log.d(TAG, "Data: $data ")
                        photoList.layoutManager = GridLayoutManager(context, 3)

                        if (data != null) {
                            photoList.adapter = PhotoAdapter(data)
                        }

                    }
                }

            }


        }
    }
    private fun getLastLocation() {
        getNewLocation()
        if (checkPermission()) {
            if (isLocationEnabled()) {

                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {

                    } else {
                        Toast.makeText(requireActivity(), "lat: ${location.latitude} long ${location.longitude} ", Toast.LENGTH_SHORT).show()
                        lat = location.latitude.toString()
                        long = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Please Enable Your Location service", Toast.LENGTH_SHORT).show()
            }
        } else {
            RequestUserPermission()
        }
    }

    private fun checkPermission(): Boolean {
//check user Permissions
        if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun RequestUserPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID
        )
    }

    //check if the location service is enabled
    private fun isLocationEnabled(): Boolean {
        var locationManger = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManger.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //checks permission result
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Debug", "You have the premission")
            }
        }
    }


    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            return
        }

    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            val lastLocation = p0!!.lastLocation
            lat = lastLocation.latitude.toString()
            long = lastLocation.longitude.toString()
            Toast.makeText(requireActivity(), "lat: ${lastLocation.latitude} long ${lastLocation.longitude} ", Toast.LENGTH_SHORT).show()

        }
    }


}