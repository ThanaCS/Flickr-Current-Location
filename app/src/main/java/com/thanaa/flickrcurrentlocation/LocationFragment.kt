package com.thanaa.flickrcurrentlocation

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*

private var PERMISSION_ID = 1 //any unique number
var lat: Double = 0.0
var long: Double = 0.0

class LocationFragment : Fragment(R.layout.fragment_location) {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLastLocation()
        getNewLocation()
    }
    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {

                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
                    if (location == null) {

                    } else {
                        Toast.makeText(requireActivity(), "lat: ${location.latitude} long ${location.longitude} ", Toast.LENGTH_SHORT).show()
                        lat = location.latitude
                        long = location.longitude
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
                Log.d("Debug", "You have the permission")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            val lastLocation = p0!!.lastLocation
            lat = lastLocation.latitude
            long = lastLocation.longitude
            Toast.makeText(requireActivity(), "lat: ${lastLocation.latitude} long ${lastLocation.longitude} ", Toast.LENGTH_SHORT).show()

        }
    }

}