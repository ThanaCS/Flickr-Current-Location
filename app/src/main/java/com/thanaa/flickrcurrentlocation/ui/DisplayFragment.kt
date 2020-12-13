package com.thanaa.flickrcurrentlocation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanaa.flickrcurrentlocation.databinding.FragmentDisplayBinding
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel


class DisplayFragment : Fragment() {
    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FlickrViewModel
    private val args by navArgs<DisplayFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = FlickrViewModel()

        binding.displayLayout.visibility = View.GONE
        viewModel.getPhotoInfo(args.photo.id)

        viewModel.photoInfoLiveData.observe(viewLifecycleOwner, {
            loadImage(args.url)
            binding.descriptionText.text = it.description._content
            binding.usernameText.text = "@" + it.owner.username
            binding.commentsNumber.text = it.comments._content + " comments"
            binding.titleText.text = it.title._content
            binding.dateText.text = it.dates.taken
            binding.viewsText.text = it.views + " views"
            binding.countryText.text = args.location.country._content
            binding.regionText.text = "${args.location.region._content}, "
            binding.displayLayout.visibility = View.VISIBLE

        })

        binding.locationBox.setOnClickListener {
            val action =
                DisplayFragmentDirections.actionDisplayFragmentToMapsFragment22(args.location)
            findNavController().navigate(action)
        }
    }

    private fun loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(900, 1024))
            .centerCrop()
            .into(binding.imgPreview)
    }

    override fun onDestroy() {
        super.onDestroy()
        //to avoid memory leaks
        _binding = null
    }


}