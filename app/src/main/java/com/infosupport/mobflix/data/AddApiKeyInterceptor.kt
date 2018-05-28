package com.infosupport.mobflix.data

import com.infosupport.mobflix.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AddApiKeyInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
