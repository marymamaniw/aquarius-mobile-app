package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.repository.AuthRepository

class CheckUserLoggedInUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}
