package com.marymamani.aquariusapp.data.datasource.remote.models

data class EmployeeResponse(
    val code: String,
    val name: String,
    val area: AreaResponse,
    val email: String,
    val phoneNumber: String,
)
