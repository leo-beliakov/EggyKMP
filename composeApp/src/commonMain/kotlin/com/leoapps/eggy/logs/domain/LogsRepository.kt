package com.leoapps.eggy.logs.domain

import com.leoapps.eggy.logs.domain.model.Log
import kotlinx.coroutines.flow.Flow

interface LogsRepository {
    fun getLogs(): Flow<List<Log>>
    suspend fun addLog(log: Log)
    suspend fun clearLogs()
}