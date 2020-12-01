package com.thanaa.flickrcurrentlocation.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanaa.flickrcurrentlocation.R
import com.thanaa.flickrcurrentlocation.model.Photo
import com.thanaa.flickrcurrentlocation.util.toUrl
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel
import kotlinx.android.synthetic.main.row_item.view.*

private var TAG = "PhotoAdapter"
class PhotoAdapter(private val photos: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {
    lateinit var viewModel: FlickrViewModel

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


        val url = toUrl(photoItem.server, photoItem.id, photoItem.secret)
        Glide.with(holder.itemImage).load(url).apply(RequestOptions().override(500, 500))
                .centerCrop().into(holder.itemImage)

        holder.itemView.image_view.setOnClickListener { view ->
            val title = photoItem.title
            val country = viewModel.locationLiveData.value?.country?._content ?: ""
            val region = viewModel.locationLiveData.value?.region?._content ?: ""
            val accuracy = viewModel.locationLiveData.value?.accuracy ?: ""
            val locality = viewModel.locationLiveData.value?.locality?._content ?: ""

            val builder: AlertDialog.Builder? = view.context.let {
                AlertDialog.Builder(it)
            }
            builder?.setMessage("$title\n$country\n$region\n$accuracy\n$locality ")
            builder?.apply {
                setPositiveButton("Show more",
                        DialogInterface.OnClickListener { dialog, id ->
                            val action = LocationFragmentDirections.actionLocationFragmentToDisplayFragment(viewModel.locationLiveData.value!!, url)
                            holder.itemView.image_view.findNavController().navigate(action)
                        })

                val dialog: AlertDialog? = builder.create()
                dialog?.show()


            }

        }
    }


}