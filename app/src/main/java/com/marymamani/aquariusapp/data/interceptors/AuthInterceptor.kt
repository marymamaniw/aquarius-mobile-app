package com.marymamani.aquariusapp.data.interceptors

import com.marymamani.aquariusapp.data.datasource.local.AuthLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authLocalDataSource: AuthLocalDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = authLocalDataSource.getUserToken()
        val request = chain.request().newBuilder()
            .apply {
                if (!token.isNullOrEmpty()) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(request)
    }
}
