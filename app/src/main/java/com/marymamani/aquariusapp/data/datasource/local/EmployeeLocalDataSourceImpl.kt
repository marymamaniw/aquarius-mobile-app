package com.marymamani.aquariusapp.data.datasource.local

import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeDao
import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeEntity
import javax.inject.Inject

class EmployeeLocalDataSourceImpl @Inject constructor(
    private val employeeDao: EmployeeDao
): EmployeeLocalDataSource {
    override suspend fun saveEmployees(employeesList: List<EmployeeEntity>) {
        employeesList.forEach {
            employeeDao.insertEmployee(it)
        }
    }

    override suspend fun getAllEmployees(): List<EmployeeEntity>? {
        return employeeDao.getAllEmployee()
    }

    override suspend fun getEmployeeById(id: Int): EmployeeEntity? {
        return employeeDao.getEmployeeById(id)
    }

    override suspend fun updateEmployeeById(employeeEntity: EmployeeEntity) {
        employeeDao.updateEmployee(employeeEntity)
    }

    override suspend fun deleteEmployeeById(id: Int) {
        employeeDao.deleteEmployee(id)
    }

    override suspend fun deleteAllEmployees() {
        employeeDao.deleteAll()
    }
}