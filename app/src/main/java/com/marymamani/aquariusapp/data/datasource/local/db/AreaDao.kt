package com.marymamani.aquariusapp.data.datasource.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AreaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(area: AreaEntity)

    @Query("SELECT * FROM areas")
    suspend fun getAllArea(): List<AreaEntity>?

    @Query("SELECT * FROM areas WHERE code=:code")
    suspend fun getAreaByCode(code: String): AreaEntity?

    @Query("DELETE FROM areas")
    suspend fun deleteAll()
}