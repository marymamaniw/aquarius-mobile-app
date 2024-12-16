package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.repository.AuthRepository
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository

class LogOutUseCase(
    private val employeeRepository: EmployeeRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): Result<Unit> {
        val result = authRepository.logoutUser()
        return if (result.isSuccess) {
            employeeRepository.deleteAllEntities()
            Result.success(Unit)
        } else {
            Result.failure(Exception())
        }
    }
}