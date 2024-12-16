package com.marymamani.aquariusapp.domain.model

data class EmployeeForm(
    val areaCode: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val code: String = ""
)
