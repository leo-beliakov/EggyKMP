package com.leoapps.eggy.logs.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.leoapps.eggy.logs.data.model.LogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    @Upsert
    suspend fun saveLog(logEntity: LogEntity)

    @Query("DELETE FROM logs")
    suspend fun clear()

    @Query("SELECT * FROM logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<LogEntity>>
}