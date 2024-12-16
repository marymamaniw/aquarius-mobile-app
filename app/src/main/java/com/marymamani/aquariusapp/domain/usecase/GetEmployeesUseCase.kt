package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.model.Employee
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository

class GetEmployeesUseCase(private val employeeRepository: EmployeeRepository) {

    suspend operator fun invoke(): Result<List<Employee>> {
        return employeeRepository.getAllEmployees()
    }
}