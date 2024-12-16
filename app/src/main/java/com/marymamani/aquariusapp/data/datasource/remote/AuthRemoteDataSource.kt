package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.LoginRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.LoginResponse

interface AuthRemoteDataSource {
    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>
    suspend fun logout(): Result<Unit>
}