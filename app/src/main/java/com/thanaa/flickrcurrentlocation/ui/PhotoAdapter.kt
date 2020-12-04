package com.thanaa.flickrcurrentlocation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.model.Photo
import com.thanaa.flickrcurrentlocation.util.toUrl
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel
import kotlinx.android.synthetic.main.row_item.view.*

private var TAG = "PhotoAdapter"
var flag = false
class PhotoAdapter(private val photos: List<Photo>) :
    RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    lateinit var viewModel: FlickrViewModel


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.image_view)
        val eyeOpenImage: ImageView = view.findViewById(R.id.eye_open)
        val eyeClosedImage: ImageView = view.findViewById(R.id.eye_close)
        val viewsNumberText: TextView = view.findViewById(R.id.view_number)
        val commentNumberText: TextView = view.findViewById(R.id.comment_number)
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
        viewModel.getGeoLocation(photoItem.id)
        viewModel.getPhotoInfo(photoItem.id)
        val url = toUrl(photoItem.server, photoItem.id, photoItem.secret)
        Glide.with(holder.itemImage)
            .load(url)
            .centerCrop().into(holder.itemImage)

        viewModel.photoInfoLiveData.observeForever {
            holder.viewsNumberText.text = it.views
            holder.commentNumberText.text = it.comments._content
        }

        //TODO:removeObserver
//            viewModel.photoInfoLiveData.removeObserver()
        holder.itemView.image_view.setOnClickListener { view ->
            if (viewModel.locationLiveData.value != null) {
                val action = LocationFragmentDirections.actionLocationFragmentToDisplayFragment(
                    viewModel.locationLiveData.value!!, url, photoItem
                )
                if (viewModel.photoInfoLiveData.value?.views != null)
                    holder.itemView.image_view.findNavController().navigate(action)

            }

        }
        eyeToggle(holder)
    }

    private fun eyeToggle(holder: ViewHolder) {

        holder.eyeClosedImage.setOnClickListener {
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
        holder.eyeOpenImage.setOnClickListener {
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