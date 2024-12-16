package com.marymamani.aquariusapp.data.datasource.local

import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeEntity

interface EmployeeLocalDataSource {
    suspend fun saveEmployees(employeesList: List<EmployeeEntity>)
    suspend fun getAllEmployees(): List<EmployeeEntity>?
    suspend fun getEmployeeById(id: Int): EmployeeEntity?
    suspend fun updateEmployeeById(employeeEntity: EmployeeEntity)
    suspend fun deleteEmployeeById(id: Int)
    suspend fun deleteAllEmployees()
}