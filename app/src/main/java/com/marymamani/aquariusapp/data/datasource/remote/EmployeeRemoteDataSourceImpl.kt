package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeResponse

class EmployeeRemoteDataSourceImpl(
    private val employeeApiService: EmployeeApiService
) : EmployeeRemoteDataSource {

    override suspend fun getAllEmployees(): Result<List<EmployeeResponse>> {
        return try {
            val response = employeeApiService.getAllEmployees()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener empleados: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createEmployee(employeeRequest: EmployeeRequest): Result<EmployeeResponse> {
        return try {
            val response = employeeApiService.createEmployee(employeeRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al crear empleado: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateEmployee(employeeCode: String, employeeRequest: EmployeeRequest): Result<EmployeeResponse> {
        return try {
            val response = employeeApiService.updateEmployee(employeeCode, employeeRequest)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al actualizar empleado: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteEmployee(employeeCode: String): Result<String> {
        return try {
            val response = employeeApiService.deleteEmployee(employeeCode)
            if (response.isSuccessful) {
                Result.success(response.body().toString())
            } else {
                Result.failure(Exception("Error al eliminar empleado: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
