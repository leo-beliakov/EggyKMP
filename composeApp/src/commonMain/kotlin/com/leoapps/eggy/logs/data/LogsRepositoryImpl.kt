package com.leoapps.eggy.logs.data

import com.leoapps.eggy.logs.data.model.toDomain
import com.leoapps.eggy.logs.data.model.toEntity
import com.leoapps.eggy.logs.domain.LogsRepository
import com.leoapps.eggy.logs.domain.model.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LogsRepositoryImpl(
    private val dao: LogDao
) : LogsRepository {

    override fun getLogs(): Flow<List<Log>> {
        return dao.getAllLogs()
            .map { logs ->
                logs.map { logEntity ->
                    logEntity.toDomain()
                }
            }
    }

    override suspend fun addLog(log: Log) {
        dao.saveLog(log.toEntity())
    }

    override suspend fun clearLogs() {
        dao.clear()
    }
}