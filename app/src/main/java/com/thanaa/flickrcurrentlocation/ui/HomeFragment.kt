package com.thanaa.flickrcurrentlocation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.model.Photo
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel


private var TAG = "LocationFragment"
var PERMISSION_ID: Int = 1

class LocationFragment : Fragment(R.layout.home_location) {
    private var count = 0
    var viewModel = FlickrViewModel()
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var photoListRecyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar
    private var photosList: ArrayList<Photo> = ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = requireView().findViewById(R.id.images_progress_bar)
        photoListRecyclerView = requireView().findViewById(R.id.photo_list)

        getUserLocation()
        getData()
    }

    private fun getData() {
        if (count == 0) {
            count++
            progressBar.visibility = View.VISIBLE
            viewModel.photosLiveData.observe(viewLifecycleOwner, {
                photoListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                photoListRecyclerView.adapter = PhotoAdapter(it)
                progressBar.visibility = View.GONE
                photosList.addAll(it)
                photoListRecyclerView.visibility = View.VISIBLE
            })
        } else {
            photoListRecyclerView.adapter = PhotoAdapter(photosList)
            photoListRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun getUserLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_ID
            )
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                viewModel.getPhotos(it.latitude.toString(), it.longitude.toString())
            }
        }
    }

}