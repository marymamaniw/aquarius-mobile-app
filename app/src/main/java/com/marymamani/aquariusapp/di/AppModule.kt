package com.marymamani.aquariusapp.di

import android.content.Context
import android.content.SharedPreferences
import com.marymamani.aquariusapp.data.datasource.local.AuthLocalDataSource
import com.marymamani.aquariusapp.data.datasource.local.AuthLocalDataSourceImpl
import com.marymamani.aquariusapp.data.datasource.remote.AuthApiService
import com.marymamani.aquariusapp.data.datasource.remote.AuthRemoteDataSource
import com.marymamani.aquariusapp.data.datasource.remote.AuthRemoteDataSourceImpl
import com.marymamani.aquariusapp.data.interceptors.AuthInterceptor
import com.marymamani.aquariusapp.data.repository.AuthRepositoryImpl
import com.marymamani.aquariusapp.domain.repository.AuthRepository
import com.marymamani.aquariusapp.domain.usecase.CheckUserLoggedInUseCase
import com.marymamani.aquariusapp.domain.usecase.LoginUserUseCase
import com.marymamani.aquariusapp.utils.NetworkHelper
import com.marymamani.aquariusapp.utils.NetworkHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://192.168.101.19:8080/api/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(authLocalDataSource: AuthLocalDataSource): AuthInterceptor {
        return AuthInterceptor(authLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(sharedPreferences: SharedPreferences): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authService: AuthApiService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        localDataSource: AuthLocalDataSource,
        remoteDataSource: AuthRemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCheckUserLoggedInUseCase(
        authRepository: AuthRepository
    ): CheckUserLoggedInUseCase {
        return CheckUserLoggedInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUserUseCase(
        authRepository: AuthRepository
    ): LoginUserUseCase {
        return LoginUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkHelper {
        return NetworkHelperImpl(context)
    }
}