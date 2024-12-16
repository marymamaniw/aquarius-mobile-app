package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.LoginRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("v1/auth/login")
    suspend fun login(@Body data: LoginRequest): Response<LoginResponse>

    @POST("v1/auth/logout")
    suspend fun logout(): Response<Unit>
}