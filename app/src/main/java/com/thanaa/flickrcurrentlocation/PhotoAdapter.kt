package com.thanaa.flickrcurrentlocation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PhotoAdapter(private val photos: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var TAG = "PhotoAdapter"

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false))
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val photoItem = photos[position]
        val url = "https://live.staticflickr.com/${photoItem.server}/${photoItem.id}_${photoItem.secret}.jpg"
        Log.d(TAG, "url")
        Glide.with(holder.itemImage).load(url).apply(RequestOptions().override(500, 500))
                .centerCrop().into(holder.itemImage)

    }

}