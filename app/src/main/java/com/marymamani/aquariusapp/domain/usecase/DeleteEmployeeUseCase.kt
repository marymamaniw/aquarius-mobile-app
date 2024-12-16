package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.repository.EmployeeRepository

class DeleteEmployeeUseCase(
    private val employeeRepository: EmployeeRepository
) {

    suspend operator fun invoke(employeeCode: String, employeeId: Int): Result<String> {
        return employeeRepository.deleteEmployee(employeeCode, employeeId)
    }
}