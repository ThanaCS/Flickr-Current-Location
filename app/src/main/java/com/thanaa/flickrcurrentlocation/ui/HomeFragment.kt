package com.thanaa.flickrcurrentlocation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel


private var TAG = "LocationFragment"
var PERMISSION_ID: Int = 1

class LocationFragment : Fragment(R.layout.home_location) {

    lateinit var viewModel: FlickrViewModel
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var photoListRecyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FlickrViewModel::class.java]
        progressBar = requireView().findViewById(R.id.images_progress_bar)
        photoListRecyclerView = requireView().findViewById(R.id.photo_list)
        getUserLocation()
        getData()
    }

    private fun getData() {

        progressBar.visibility = View.VISIBLE
        photoListRecyclerView.visibility = View.GONE
        viewModel.photosLiveData.observe(viewLifecycleOwner, {
            photoListRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            photoListRecyclerView.adapter = PhotoAdapter(it)
            progressBar.visibility = View.GONE
            photoListRecyclerView.visibility = View.VISIBLE
        })
        photoListRecyclerView.visibility = View.VISIBLE

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