package com.marymamani.aquariusapp.data.datasource.local

import com.marymamani.aquariusapp.data.datasource.local.db.AreaDao
import com.marymamani.aquariusapp.data.datasource.local.db.AreaEntity
import javax.inject.Inject

class AreaLocalDataSourceImpl @Inject constructor(
    private val areaDao: AreaDao
): AreaLocalDataSource {
    override suspend fun saveAreas(areasList: List<AreaEntity>) {
        areasList.forEach {
            areaDao.insertArea(it)
        }
    }

    override suspend fun getAllAreas(): List<AreaEntity>? {
        return areaDao.getAllArea()
    }

    override suspend fun getAreaByCode(code: String): AreaEntity? {
        return areaDao.getAreaByCode(code)
    }

    override suspend fun deleteAllAreas() {
        areaDao.deleteAll()
    }
}