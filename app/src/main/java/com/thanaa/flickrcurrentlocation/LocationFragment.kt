package com.thanaa.flickrcurrentlocation
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


private var TAG = "LocationFragment"

var PERMISSION_ID: Int = 1

class LocationFragment : Fragment(R.layout.fragment_location) {

    lateinit var viewModel: FlickrViewModel
    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var photoList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = FlickrViewModel()
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


        fetchData()
    }

    private fun fetchData() {
        photoList = requireView().findViewById(R.id.photo_list)

        viewModel.photosLiveData.observe(viewLifecycleOwner, {
            photoList.layoutManager = GridLayoutManager(requireContext(), 3)
            photoList.adapter = PhotoAdapter(it)

            showPreview(it)
        })
    }

    private fun showPreview(photo: List<Photo>) {
        //Todo:preview of a ThumbNail

    }
}