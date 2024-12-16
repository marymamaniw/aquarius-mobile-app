package com.marymamani.aquariusapp.domain.repository

import com.marymamani.aquariusapp.domain.model.Area

interface AreaRepository {
    suspend fun getAllAreas(): Result<List<Area>>
}