package com.marymamani.aquariusapp.domain.repository

import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.model.EmployeeForm

interface EmployeeRepository {
    suspend fun getAllEmployees(): Result<List<Employee>>
    suspend fun createEmployee(employee: EmployeeForm, idEmployee: Int): Result<Unit>
    suspend fun updateEmployee(employeeCode: String, employee: EmployeeForm, employeeId: Int): Result<Unit>
    suspend fun deleteEmployee(employeeCode: String, employeeId: Int): Result<String>
    suspend fun getEmployeeById(employeeId: Int, ): Result<Employee?>
    suspend fun deleteAllEntities()
}