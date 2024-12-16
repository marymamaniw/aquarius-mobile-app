package com.marymamani.aquariusapp.di

import com.marymamani.aquariusapp.data.datasource.local.AreaLocalDataSource
import com.marymamani.aquariusapp.data.datasource.local.AreaLocalDataSourceImpl
import com.marymamani.aquariusapp.data.datasource.local.EmployeeLocalDataSource
import com.marymamani.aquariusapp.data.datasource.local.EmployeeLocalDataSourceImpl
import com.marymamani.aquariusapp.data.datasource.local.db.AreaDao
import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeDao
import com.marymamani.aquariusapp.data.datasource.remote.AreaApiService
import com.marymamani.aquariusapp.data.datasource.remote.AreaRemoteDataSource
import com.marymamani.aquariusapp.data.datasource.remote.AreaRemoteDataSourceImpl
import com.marymamani.aquariusapp.data.datasource.remote.EmployeeApiService
import com.marymamani.aquariusapp.data.datasource.remote.EmployeeRemoteDataSource
import com.marymamani.aquariusapp.data.datasource.remote.EmployeeRemoteDataSourceImpl
import com.marymamani.aquariusapp.data.repository.AreaRepositoryImpl
import com.marymamani.aquariusapp.data.repository.EmployeeRepositoryImpl
import com.marymamani.aquariusapp.domain.repository.AreaRepository
import com.marymamani.aquariusapp.domain.repository.EmployeeRepository
import com.marymamani.aquariusapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAreaApiService(retrofit: Retrofit): AreaApiService {
        return retrofit.create(AreaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAreaRemoteDataSource(areaApiService: AreaApiService): AreaRemoteDataSource {
        return AreaRemoteDataSourceImpl(areaApiService)
    }

    @Provides
    @Singleton
    fun provideAreaRepository(
        areaRemoteDataSource: AreaRemoteDataSource,
        areaLocalDataSource: AreaLocalDataSource,
        networkHelper: NetworkHelper
    ): AreaRepository {
        return AreaRepositoryImpl(areaRemoteDataSource, areaLocalDataSource, networkHelper)
    }

    @Provides
    @Singleton
    fun provideEmployeeApiService(retrofit: Retrofit): EmployeeApiService {
        return retrofit.create(EmployeeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideEmployeeRemoteDataSource(employeeApiService: EmployeeApiService): EmployeeRemoteDataSource {
        return EmployeeRemoteDataSourceImpl(employeeApiService)
    }

    @Provides
    @Singleton
    fun provideEmployeeRepository(
        employeeRemoteDataSource: EmployeeRemoteDataSource,
        employeeLocalDataSource: EmployeeLocalDataSource,
        networkHelper: NetworkHelper,
        areaLocalDataSource: AreaLocalDataSource
    ): EmployeeRepository {
        return EmployeeRepositoryImpl(employeeRemoteDataSource, employeeLocalDataSource, areaLocalDataSource, networkHelper)
    }

    @Provides
    @Singleton
    fun provideEmployeeLocalDataSource(employeeDao: EmployeeDao): EmployeeLocalDataSource {
        return EmployeeLocalDataSourceImpl(employeeDao)
    }

    @Provides
    @Singleton
    fun provideAreaLocalDataSource(areaDao: AreaDao): AreaLocalDataSource {
        return AreaLocalDataSourceImpl(areaDao)
    }
}