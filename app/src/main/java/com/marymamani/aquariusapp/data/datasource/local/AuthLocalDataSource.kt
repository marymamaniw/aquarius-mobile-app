package com.marymamani.aquariusapp.data.datasource.local

interface AuthLocalDataSource {
    fun isUserLoggedIn(): Boolean
    fun saveUserToken(token: String, refreshToken: String, userName: String)
    fun clearUserData()
    fun getUserToken(): String?
    fun getRefreshToken(): String?
    fun getUserName(): String?
}