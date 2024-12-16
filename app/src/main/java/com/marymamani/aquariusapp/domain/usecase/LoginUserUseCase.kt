package com.marymamani.aquariusapp.domain.usecase

import com.marymamani.aquariusapp.domain.model.UserCredentials
import com.marymamani.aquariusapp.domain.model.UserInfo
import com.marymamani.aquariusapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(credentials: UserCredentials): Result<Unit> {
        return authRepository.loginUser(credentials)
    }
}