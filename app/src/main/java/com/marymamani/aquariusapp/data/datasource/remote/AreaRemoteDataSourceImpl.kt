package com.marymamani.aquariusapp.data.datasource.remote

import com.google.gson.Gson
import com.marymamani.aquariusapp.data.datasource.remote.models.AreaResponse
import com.marymamani.aquariusapp.data.datasource.remote.models.ErrorResponse
import javax.inject.Inject

class AreaRemoteDataSourceImpl @Inject constructor(
    private val areaApiService: AreaApiService
) : AreaRemoteDataSource {

    override suspend fun getAllAreas(): Result<List<AreaResponse>> {
        return try {
            val response = areaApiService.getAllAreas()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (e: Exception) {
                    ErrorResponse(message = "Error desconocido")
                }
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}