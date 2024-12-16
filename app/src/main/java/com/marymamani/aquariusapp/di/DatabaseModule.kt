package com.marymamani.aquariusapp.di

import android.content.Context
import androidx.room.Room
import com.marymamani.aquariusapp.data.datasource.local.db.AppDatabase
import com.marymamani.aquariusapp.data.datasource.local.db.AreaDao
import com.marymamani.aquariusapp.data.datasource.local.db.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "aquarius_database"
        ).build()
    }

    @Provides
    fun provideEmployeeDao(database: AppDatabase): EmployeeDao {
        return database.employeeDao()
    }

    @Provides
    fun provideAreaDao(database: AppDatabase): AreaDao {
        return database.areaDao()
    }
}