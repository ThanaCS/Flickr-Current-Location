package com.thanaa.flickrcurrentlocation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanaa.flickrcurrentlocation.R
import kotlinx.android.synthetic.main.fragment_display.*

class DisplayFragment : Fragment() {
    private lateinit var countryText: TextView
    private lateinit var titleText: TextView
    private lateinit var regionText: TextView
    private val args by navArgs<DisplayFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countryText = view.findViewById(R.id.country_text)
        titleText = view.findViewById(R.id.title_text)
        regionText = view.findViewById(R.id.region_text)

        titleText.text = args.photo.title
        countryText.text = args.location.country._content
        regionText.text = "${args.location.region._content}, "


        resizeImage(args.url)
        location_box.setOnClickListener {

        val action =
                DisplayFragmentDirections.actionDisplayFragmentToMapsFragment22(args.location)
            findNavController().navigate(action)
        }
    }

    private fun resizeImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(900, 1024))
            .centerCrop()
            //  .transform( RoundedCorners(1000))
            .into(img_preview)

    }


}