package com.marymamani.aquariusapp.data.datasource.remote

import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeRequest
import com.marymamani.aquariusapp.data.datasource.remote.models.EmployeeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EmployeeApiService {
    @GET("v1/employees")
    suspend fun getAllEmployees(): Response<List<EmployeeResponse>>

    @POST("v1/employees")
    suspend fun createEmployee(@Body employeeRequest: EmployeeRequest): Response<EmployeeResponse>

    @PUT("v1/employees/{employeeCode}")
    suspend fun updateEmployee(
        @Path("employeeCode") employeeCode: String, @Body employeeRequest: EmployeeRequest
    ): Response<EmployeeResponse>

    @DELETE("v1/employees/{employeeCode}")
    suspend fun deleteEmployee(@Path("employeeCode") employeeCode: String
    ): Response<String>
}