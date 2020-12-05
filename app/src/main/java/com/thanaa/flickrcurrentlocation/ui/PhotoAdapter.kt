package com.thanaa.flickrcurrentlocation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.model.Photo
import com.thanaa.flickrcurrentlocation.util.toUrl
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel
import kotlinx.android.synthetic.main.row_item.view.*

private var TAG = "PhotoAdapter"


class PhotoAdapter(private val photos: List<Photo>) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    lateinit var viewModel: FlickrViewModel

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemImage: ImageView = view.findViewById(R.id.image_view)
        private val eyeOpenImage: ImageView = view.findViewById(R.id.eye_open)
        private val eyeClosedImage: ImageView = view.findViewById(R.id.eye_close)
        private val viewsNumberText: TextView = view.findViewById(R.id.view_number)
        private val commentNumberText: TextView = view.findViewById(R.id.comment_number)

        fun bind(holder: ViewHolder, url: String, views: String, comments: String) {

            Glide.with(holder.itemView.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(itemImage)

            viewsNumberText.text = views
            commentNumberText.text = comments

        }

        fun eyeToggle(holder: ViewHolder) {
            var flag = false
            eyeClosedImage.setOnClickListener {
                if (!flag) {
                    holder.eyeClosedImage.visibility = View.GONE
                    holder.eyeOpenImage.visibility = View.VISIBLE
                    flag = true
                } else {

                    holder.eyeClosedImage.visibility = View.VISIBLE
                    holder.eyeOpenImage.visibility = View.GONE
                    flag = false
                }
            }
            eyeOpenImage.setOnClickListener {
                if (!flag) {
                    holder.eyeClosedImage.visibility = View.GONE
                    holder.eyeOpenImage.visibility = View.VISIBLE
                    flag = true
                } else {
                    holder.eyeClosedImage.visibility = View.VISIBLE
                    holder.eyeOpenImage.visibility = View.GONE
                    flag = false
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        viewModel = FlickrViewModel()
        val photoItem: Photo = photos[position]
        val url = toUrl(photoItem.server, photoItem.id, photoItem.secret)
        viewModel.getGeoLocation(photoItem.id)
        viewModel.getPhotoInfo(photoItem.id)

        viewModel.photoInfoLiveData.observeForever {
            holder.bind(holder, url, it.views, it.comments._content)
        }
        holder.eyeToggle(holder)

        //passing data to DisplayFragment and navigating
        holder.itemView.image_view.setOnClickListener { view ->
            if (viewModel.locationLiveData.value != null) {
                val action = LocationFragmentDirections.actionLocationFragmentToDisplayFragment(
                    viewModel.locationLiveData.value!!, url, photoItem
                )
                if (viewModel.photoInfoLiveData.value?.views != null)
                    holder.itemView.image_view.findNavController().navigate(action)

            }

        }

    }


}