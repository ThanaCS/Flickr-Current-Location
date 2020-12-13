package com.thanaa.flickrcurrentlocation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.thanaa.flickrcurrentlocation.databinding.FragmentHomeBinding
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel


private var TAG = "LocationFragment"
var PERMISSION_ID: Int = 1

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: FlickrViewModel
    lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FlickrViewModel::class.java]
        getUserLocation()
        getData()
    }

    private fun getData() {
        binding.imagesProgressBar.visibility = View.VISIBLE

        viewModel.photosLiveData.observe(viewLifecycleOwner, {
            binding.flickrRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.flickrRecyclerView.adapter = PhotoAdapter(it)
            binding.imagesProgressBar.visibility = View.GONE
        })


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

    override fun onDestroy() {
        super.onDestroy()
        //to avoid memory leaks
        _binding = null
    }


}