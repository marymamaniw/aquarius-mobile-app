package com.marymamani.aquariusapp.data.repository

import com.marymamani.aquariusapp.data.datasource.local.AuthLocalDataSource
import com.marymamani.aquariusapp.data.datasource.remote.AuthRemoteDataSource
import com.marymamani.aquariusapp.data.mappers.toLoginRequest
import com.marymamani.aquariusapp.domain.model.UserCredentials
import com.marymamani.aquariusapp.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun isUserLoggedIn(): Boolean {
        return localDataSource.isUserLoggedIn()
    }

    override suspend fun loginUser(credentials: UserCredentials): Result<Unit> {
        val authResponseResult = remoteDataSource.login(credentials.toLoginRequest())

        return if (authResponseResult.isSuccess) {
            val authResponse = authResponseResult.getOrNull()
            authResponse?.let {
                localDataSource.saveUserToken(it.token, it.refreshToken, it.name)
                Result.success(Unit)
            } ?: Result.failure(Exception("Error al procesar la respuesta del servidor"))
        } else {
            Result.failure(authResponseResult.exceptionOrNull() ?: Exception("Error desconocido"))
        }
    }

    override suspend fun logoutUser(): Result<Unit> {
        val logoutResult = remoteDataSource.logout()
        return if (logoutResult.isSuccess) {
            localDataSource.clearUserData()
            Result.success(Unit)
        } else {
            Result.failure(logoutResult.exceptionOrNull() ?: Exception("Error desconocido"))
        }
    }
}