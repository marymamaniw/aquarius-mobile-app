package com.marymamani.aquariusapp.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity)

    @Query("SELECT * FROM employees")
    suspend fun getAllEmployee(): List<EmployeeEntity>?

    @Query("SELECT * FROM employees WHERE id=:id")
    suspend fun getEmployeeById(id: Int): EmployeeEntity?

    @Update
    suspend fun updateEmployee(employee: EmployeeEntity)

    @Query("DELETE FROM employees WHERE id=:id")
    suspend fun deleteEmployee(id: Int)

    @Query("DELETE FROM employees")
    suspend fun deleteAll()
}