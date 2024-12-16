package com.marymamani.aquariusapp.data.datasource.remote

import com.google.gson.Gson
import com.marymamani.aquariusapp.data.datasource.remote.models.ErrorResponse
import com.marymamani.aquariusapp.data.datasource.remote.models.LoginRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.LoginResponse
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthApiService
) : AuthRemoteDataSource {

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return try {
            val response = authService.login(loginRequest)
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

    override suspend fun logout(): Result<Unit> {
        return try {
            val response = authService.logout()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al cerrar sesión: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}