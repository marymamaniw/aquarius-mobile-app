package com.marymamani.aquariusapp.domain.model

data class Employee(
    val id: Int,
    val code: String,
    val name: String,
    val area: Area?,
    val email: String,
    val phoneNumber: String,
)