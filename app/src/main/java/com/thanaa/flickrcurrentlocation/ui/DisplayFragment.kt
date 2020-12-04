package com.thanaa.flickrcurrentlocation.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel
import kotlinx.android.synthetic.main.fragment_display.*

class DisplayFragment : Fragment(R.layout.fragment_display) {
    private lateinit var countryText: TextView
    private lateinit var titleText: TextView
    private lateinit var regionText: TextView
    private lateinit var descriptionText: TextView
    private lateinit var usernameText: TextView
    private lateinit var commentsText: TextView
    private lateinit var dateText: TextView
    private lateinit var viewsText: TextView

    private lateinit var viewModel: FlickrViewModel
    private val args by navArgs<DisplayFragmentArgs>()
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = FlickrViewModel()

        countryText = view.findViewById(R.id.country_text)
        titleText = view.findViewById(R.id.title_text)
        regionText = view.findViewById(R.id.region_text)
        descriptionText = view.findViewById(R.id.description_text)
        usernameText = view.findViewById(R.id.username_text)
        commentsText = view.findViewById(R.id.comments_number)
        dateText = view.findViewById(R.id.date_text)
        viewsText = view.findViewById(R.id.views_text)
        progressBar = requireView().findViewById(R.id.images_progress_bar)

        viewModel.getPhotoInfo(args.photo.id)
        progressBar.visibility = View.VISIBLE
        viewModel.photoInfoLiveData.observe(viewLifecycleOwner, {
            loadImage(args.url)
            descriptionText.text = "Description" + it.description._content
            usernameText.text = "@" + it.owner.username
            commentsText.text = "Comments:" + it.comments._content
            titleText.text = "Title" + it.title._content
            dateText.text = it.dates.taken
            viewsText.text = "Views:" + it.views
            countryText.text = args.location.country._content
            regionText.text = "${args.location.region._content}, "
            progressBar.visibility = View.GONE

        })

        location_box.setOnClickListener {
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
            .into(img_preview)
    }


}