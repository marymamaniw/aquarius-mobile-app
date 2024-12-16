package com.marymamani.aquariusapp.di

import com.marymamani.aquariusapp.domain.repository.AreaRepository
import com.marymamani.aquariusapp.domain.repository.AuthRepository
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository
import com.marymamani.aquariusapp.domain.usecase.CreateEmployeeUseCase
import com.marymamani.aquariusapp.domain.usecase.DeleteEmployeeUseCase
import com.marymamani.aquariusapp.domain.usecase.GetAreasUseCase
import com.marymamani.aquariusapp.domain.usecase.GetEmployeeByIdUseCase
import com.marymamani.aquariusapp.domain.usecase.GetEmployeesUseCase
import com.marymamani.aquariusapp.domain.usecase.LogOutUseCase
import com.marymamani.aquariusapp.domain.usecase.UpdateEmployeeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideGetAreasUseCase(areaRepository: AreaRepository): GetAreasUseCase {
        return GetAreasUseCase(areaRepository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeesUseCase(employeeRepository: EmployeeRepository): GetEmployeesUseCase {
        return GetEmployeesUseCase(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideCreateEmployeeUseCase(employeeRepository: EmployeeRepository): CreateEmployeeUseCase {
        return CreateEmployeeUseCase(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateEmployeeUseCase(employeeRepository: EmployeeRepository): UpdateEmployeeUseCase {
        return UpdateEmployeeUseCase(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteEmployeeUseCase(employeeRepository: EmployeeRepository): DeleteEmployeeUseCase {
        return DeleteEmployeeUseCase(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideGetEmployeeByIdUseCase(employeeRepository: EmployeeRepository): GetEmployeeByIdUseCase {
        return GetEmployeeByIdUseCase(employeeRepository)
    }

    @Provides
    @Singleton
    fun provideLogOutUseCase(
        employeeRepository: EmployeeRepository,
        authRepository: AuthRepository
    ): LogOutUseCase {
        return LogOutUseCase(employeeRepository, authRepository)
    }
}