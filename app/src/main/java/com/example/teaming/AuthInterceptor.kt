package com.example.teaming

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req =
            chain.request().newBuilder().addHeader("Authorization", App.prefs.token ?: "").build()

        Log.d("return","${req}")
        return chain.proceed(req)
    }
}
