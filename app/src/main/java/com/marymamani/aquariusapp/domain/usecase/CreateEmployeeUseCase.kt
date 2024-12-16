package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.model.EmployeeForm
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository

class CreateEmployeeUseCase(private val employeeRepository: EmployeeRepository) {

    suspend operator fun invoke(employeeForm: EmployeeForm): Result<Unit> {
        return employeeRepository.createEmployee(employeeForm, 0)
    }
}