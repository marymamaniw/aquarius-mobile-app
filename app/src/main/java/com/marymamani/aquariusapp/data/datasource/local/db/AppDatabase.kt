package com.marymamani.aquariusapp.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EmployeeEntity::class, AreaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun areaDao(): AreaDao
}