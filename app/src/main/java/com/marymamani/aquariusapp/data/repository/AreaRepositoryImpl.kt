package com.marymamani.aquariusapp.data.repository

import com.marymamani.aquariusapp.data.datasource.local.AreaLocalDataSource
import com.marymamani.aquariusapp.data.datasource.remote.AreaRemoteDataSource
import com.marymamani.aquariusapp.data.mappers.toArea
import com.marymamani.aquariusapp.data.mappers.toAreaEntity
import com.marymamani.aquariusapp.domain.model.Area
import com.marymamani.aquariusapp.domain.repository.AreaRepository
import com.marymamani.aquariusapp.utils.NetworkHelper

class AreaRepositoryImpl(
    private val areaRemoteDataSource: AreaRemoteDataSource,
    private val areaLocalDataSource: AreaLocalDataSource,
    private val networkHelper: NetworkHelper
) : AreaRepository {

    override suspend fun getAllAreas(): Result<List<Area>> {
        if (networkHelper.isNetworkAvailable()) {
            val areaResponseResult = areaRemoteDataSource.getAllAreas()

            return if (areaResponseResult.isSuccess) {
                val areaResponseList = areaResponseResult.getOrNull()
                areaResponseList?.let { responses ->
                    val areaList = responses.map { it.toArea()}
                    areaLocalDataSource.saveAreas(responses.map { it.toAreaEntity() })
                    Result.success(areaList)
                } ?: Result.failure(Exception("Error al procesar la respuesta del servidor"))
            } else {
                Result.failure(areaResponseResult.exceptionOrNull() ?: Exception("Error desconocido"))
            }
        } else {
            return Result.success(
                areaLocalDataSource.getAllAreas()?.map { it.toArea() } ?: emptyList())
        }
    }
}