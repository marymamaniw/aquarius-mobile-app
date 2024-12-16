package com.marymamani.aquariusapp.data.datasource.local

import com.marymamani.aquariusapp.data.datasource.local.db.AreaEntity

interface AreaLocalDataSource {
    suspend fun saveAreas(areasList: List<AreaEntity>)
    suspend fun getAllAreas(): List<AreaEntity>?
    suspend fun getAreaByCode(code: String): AreaEntity?
    suspend fun deleteAllAreas()
}