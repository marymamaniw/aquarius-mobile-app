package com.marymamani.aquariusapp.domain.repository

import com.marymamani.aquariusapp.domain.model.UserCredentials

interface AuthRepository {
    suspend fun isUserLoggedIn(): Boolean
    suspend fun loginUser(credentials: UserCredentials): Result<Unit>
    suspend fun logoutUser(): Result<Unit>
}
