package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository

class GetEmployeeByIdUseCase(private val employeeRepository: EmployeeRepository) {

    suspend operator fun invoke(employeeId: Int): Result<Employee?> {
        return employeeRepository.getEmployeeById(employeeId)
    }
}