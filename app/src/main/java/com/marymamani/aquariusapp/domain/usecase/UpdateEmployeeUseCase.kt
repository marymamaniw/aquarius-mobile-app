package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.model.EmployeeForm
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository

class UpdateEmployeeUseCase(private val employeeRepository: EmployeeRepository) {

    suspend operator fun invoke(employeeCode: String, employeeForm: EmployeeForm, employeeId: Int): Result<Unit> {
        return employeeRepository.updateEmployee(employeeCode, employeeForm, employeeId)
    }
}