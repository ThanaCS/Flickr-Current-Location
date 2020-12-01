package com.thanaa.flickrcurrentlocation.ui

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.model.Photo
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel
import kotlinx.android.synthetic.main.row_item.view.*

class PhotoAdapter(private val photos: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    lateinit var viewModel: FlickrViewModel
    var TAG = "PhotoAdapter"
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false))
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewModel = FlickrViewModel()
        val photoItem = photos[position]
        viewModel.getGeoLocation(photoItem.id)

        val url = "https://live.staticflickr.com/${photoItem.server}/${photoItem.id}_${photoItem.secret}.jpg"
        Log.d(TAG, "url")
        Glide.with(holder.itemImage).load(url).apply(RequestOptions().override(500, 500))
                .centerCrop().into(holder.itemImage)


        holder.itemView.image_view.setOnClickListener { view ->

            val builder: AlertDialog.Builder? = view.context?.let {
                AlertDialog.Builder(it)
            }
            val country = viewModel.locationLiveData.value?.country?._content ?: ""
            val region = viewModel.locationLiveData.value?.region?._content ?: ""
            val accuracy = viewModel.locationLiveData.value?.accuracy ?: ""
            val locality = viewModel.locationLiveData.value?.locality?._content ?: ""
            builder?.setMessage("${photoItem.title} ${country}\n ${region}\n ${accuracy} \n${locality} ")
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }


    }

}