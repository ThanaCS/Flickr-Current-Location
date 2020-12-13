package com.thanaa.flickrcurrentlocation.util



fun toUrl(server: String, id: String, secret: String): String {
    return "https://live.staticflickr.com/${server}/${id}_${secret}.jpg"
}
