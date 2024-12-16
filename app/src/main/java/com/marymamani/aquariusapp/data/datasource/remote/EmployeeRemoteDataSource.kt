package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeResponse

interface EmployeeRemoteDataSource {
    suspend fun getAllEmployees(): Result<List<EmployeeResponse>>
    suspend fun createEmployee(employeeRequest: EmployeeRequest): Result<EmployeeResponse>
    suspend fun updateEmployee(employeeCode: String, employeeRequest: EmployeeRequest): Result<EmployeeResponse>
    suspend fun deleteEmployee(employeeCode: String): Result<String>
}