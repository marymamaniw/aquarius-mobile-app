package com.marymamani.aquariusapp.data.repository

import com.marymamani.aquariusapp.data.datasource.local.AreaLocalDataSource
import com.marymamani.aquariusapp.data.datasource.local.EmployeeLocalDataSource
import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeEntity
import com.marymamani.aquariusapp.data.datasource.remote.EmployeeRemoteDataSource
import com.marymamani.aquariusapp.data.mappers.toArea
import com.marymamani.aquariusapp.data.mappers.toEmployee
import com.marymamani.aquariusapp.data.mappers.toEmployeeEntity
import com.marymamani.aquariusapp.data.mappers.toEmployeeForm
import com.marymamani.aquariusapp.data.mappers.toEmployeeRequest
import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.model.EmployeeForm
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository
import com.marymamani.aquariusapp.utils.NetworkHelper

class EmployeeRepositoryImpl(
    private val employeeRemoteDataSource: EmployeeRemoteDataSource,
    private val employeeLocalDataSource: EmployeeLocalDataSource,
    private val areaLocalDataSource: AreaLocalDataSource,
    private val networkHelper: NetworkHelper
) : EmployeeRepository {
    override suspend fun getAllEmployees(): Result<List<Employee>> {
        if (networkHelper.isNetworkAvailable()) {
            val responseResult = employeeRemoteDataSource.getAllEmployees()

            val listEmployees = employeeLocalDataSource.getAllEmployees()
            val toSync: List<EmployeeEntity> = listEmployees?.filter { e -> e.sync != 0 } ?: emptyList()
            if (toSync.isNotEmpty()) {
                toSync.forEach {
                    createEmployee(it.toEmployeeForm(), it.id)
                }
            }

            return if (responseResult.isSuccess) {
                val employeeResponseList = responseResult.getOrNull()
                employeeResponseList?.let { responses ->
                    employeeLocalDataSource.saveEmployees(responses.map { it.toEmployeeEntity(0) })
                    val local = employeeLocalDataSource.getAllEmployees()?.map { employee ->
                        val area = areaLocalDataSource.getAreaByCode(employee.areaCode ?: "")
                        employee.toEmployee(area?.toArea())
                    }
                    Result.success(local ?: emptyList())
                } ?: Result.failure(Exception("Error al procesar la respuesta del servidor"))
            } else {
                Result.failure(responseResult.exceptionOrNull() ?: Exception("Error desconocido"))
            }
        } else {
            val employeesLocal = employeeLocalDataSource.getAllEmployees()?.map { employee ->
                val area = areaLocalDataSource.getAreaByCode(employee.areaCode ?: "")
                employee.toEmployee(area?.toArea())
            }
            return Result.success(employeesLocal ?: emptyList())
        }
    }

    override suspend fun createEmployee(employee: EmployeeForm, idEmployee: Int): Result<Unit> {
        if (networkHelper.isNetworkAvailable()) {
            val responseResult = employeeRemoteDataSource.createEmployee(employee.toEmployeeRequest())

            return if (responseResult.isSuccess) {
                val employeeResponse = responseResult.getOrNull()
                employeeResponse?.let {
                    if (idEmployee > 0) {
                        employeeLocalDataSource.updateEmployeeById(employeeResponse.toEmployeeEntity(idEmployee))
                    } else {
                        employeeLocalDataSource.saveEmployees(listOf(employeeResponse.toEmployeeEntity(0)))
                    }
                }
                employeeResponse?.toEmployee()?.let {
                    Result.success(Unit)
                } ?: Result.failure(Exception("Error al procesar la respuesta del servidor"))
            } else {
                Result.failure(responseResult.exceptionOrNull() ?: Exception("Error desconocido"))
            }
        } else {
            employeeLocalDataSource.saveEmployees(
                listOf(employee.toEmployeeEntity())
            )
            return Result.success(Unit)
        }
    }

    override suspend fun updateEmployee(
        employeeCode: String, employee: EmployeeForm, employeeId: Int
    ): Result<Unit> {
        if (networkHelper.isNetworkAvailable()) {
            val responseResult = employeeRemoteDataSource.updateEmployee(employeeCode, employee.toEmployeeRequest())

            return if (responseResult.isSuccess) {
                val employeeResponse = responseResult.getOrNull()
                employeeResponse?.toEmployee()?.let {
                    Result.success(Unit)
                } ?: Result.failure(Exception("Error al procesar la respuesta del servidor"))
            } else {
                Result.failure(responseResult.exceptionOrNull() ?: Exception("Error desconocido"))
            }
        } else {
            employeeLocalDataSource.updateEmployeeById(EmployeeEntity(
                employeeId, employee.code, employee.name, employee.email, employee.phoneNumber, employee.areaCode, 1
            ))
            return Result.success(Unit)
        }
    }

    override suspend fun deleteEmployee(employeeCode: String, employeeId: Int): Result<String> {
        val responseResult = employeeRemoteDataSource.deleteEmployee(employeeCode)

        return if (responseResult.isSuccess) {
            val deletedCode = responseResult.getOrNull()
            deletedCode?.let {
                employeeLocalDataSource.deleteEmployeeById(employeeId)
                Result.success(it)
            } ?: Result.failure(Exception("Error al procesar la respuesta del servidor"))
        } else {
            Result.failure(responseResult.exceptionOrNull() ?: Exception("Error desconocido"))
        }
    }

    override suspend fun getEmployeeById(employeeId: Int): Result<Employee?> {
        val employee = employeeLocalDataSource.getEmployeeById(employeeId)
        if (employee != null) {
            val area = areaLocalDataSource.getAreaByCode(employee.areaCode!!)
            area?.let {
                return Result.success(employee.toEmployee(area.toArea()))
            }
        }
        return Result.success(null)
    }

    override suspend fun deleteAllEntities() {
        employeeLocalDataSource.deleteAllEmployees()
        areaLocalDataSource.deleteAllAreas()
    }
}