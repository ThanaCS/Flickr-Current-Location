package com.thanaa.flickrcurrentlocation.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface


fun toUrl(server: String, id: String, secret: String): String {
    return "https://live.staticflickr.com/${server}/${id}_${secret}.jpg"
}

fun showInformation(context: Context, title: String, country: String, region: String, accuracy: String, locality: String) {

    val builder: AlertDialog.Builder? = context.let {
        AlertDialog.Builder(it)

    }
    builder?.setMessage("$title\n$country\n$region\n$accuracy\n$locality ")
    builder?.apply {
        setPositiveButton("Show more",
                DialogInterface.OnClickListener { dialog, id ->

                })


        val dialog: AlertDialog? = builder.create()
        dialog?.show()
    }
}