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
    lateinit var countryText: TextView
    lateinit var titleText: TextView
    lateinit var regionText: TextView
    lateinit var accuracyText: TextView
    lateinit var localityText: TextView
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
        accuracyText = view.findViewById(R.id.accuracy_text)
        localityText = view.findViewById(R.id.locality_text)

        titleText.text = args.photo.title
        countryText.text = args.location.country._content
        regionText.text = args.location.region._content
        accuracyText.text = args.location.accuracy
        localityText.text = args.location.locality._content


        resizeImage(args.url)
        country_text.setOnClickListener {
            val action =
                DisplayFragmentDirections.actionDisplayFragmentToMapsFragment22(args.location)
            findNavController().navigate(action)
        }
    }

    private fun resizeImage(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(1024, 1024))
            .centerCrop()
            .into(img_preview)
    }


}