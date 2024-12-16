package com.marymamani.aquariusapp.data.mappers

import com.marymamani.aquariusapp.data.datasource.remote.models.LoginRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.LoginResponse
import com.marymamani.aquariusapp.domain.model.UserCredentials
import com.marymamani.aquariusapp.domain.model.UserInfo

fun UserCredentials.toLoginRequest(): LoginRequest {
    return LoginRequest(
        email = this.email,
        password = this.password
    )
}