package com.marymamani.aquariusapp.data.datasource.remote.models

data class ErrorResponse(
    val message: String,
    val details: String? = null
)
