package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.AreaResponse
import retrofit2.Response
import retrofit2.http.GET

interface AreaApiService {
    @GET("v1/areas")
    suspend fun getAllAreas(): Response<List<AreaResponse>>
}