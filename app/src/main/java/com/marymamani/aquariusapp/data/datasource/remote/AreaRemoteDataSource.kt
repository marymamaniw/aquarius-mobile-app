package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.AreaResponse

interface AreaRemoteDataSource {
    suspend fun getAllAreas(): Result<List<AreaResponse>>
}