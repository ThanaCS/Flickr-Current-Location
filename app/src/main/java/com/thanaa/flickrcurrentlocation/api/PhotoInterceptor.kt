package com.thanaa.flickrcurrentlocation.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private const val API_KEY = "343c1eb57437c1562e3aa3431269a9ae"

class PhotoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //you call chain.request() to access the original request
        val originalRequest: Request = chain.request()
        //originalRequest.url() pulls the original URL from the request
        val newUrl: HttpUrl = originalRequest.url().newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
            .build()
        // HttpUrl.Builder creates a new Request based on the
        // original request and overwrites the original URL with the new one
        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()


//Finally, you call chain.proceed(newRequest) to produce a Response.
// If you did not call chain.proceed(...), the network request would not happen.

        return chain.proceed(newRequest)

    }


}
