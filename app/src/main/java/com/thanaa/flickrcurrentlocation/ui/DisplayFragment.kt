package com.thanaa.flickrcurrentlocation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanaa.flickrcurrentlocation.R
import kotlinx.android.synthetic.main.fragment_display.*


class DisplayFragment : Fragment() {
    private val args by navArgs<DisplayFragmentArgs>()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_display, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resizeImage(args.url)
    }


    fun resizeImage(url: String) {
        Glide.with(this)
                .load(url)
                .apply(RequestOptions().override(1024, 1024))
                // .override(1024, 1024)
                .centerCrop()
                .into(img_preview)


    }


}