package com.leoapps.eggy.logs.data

import com.leoapps.eggy.logs.data.model.LogEntity
import com.leoapps.eggy.logs.domain.LogsRepository
import com.leoapps.eggy.logs.domain.model.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LogsRepositoryImpl(
    private val dao: LogDao
) : LogsRepository {

    // todo write mappers for this
    override fun getLogs(): Flow<List<Log>> {
        return dao.getAllLogs()
            .map { logs ->
                logs.map { logEntity ->
                    Log(
                        tag = logEntity.tag,
                        message = logEntity.message,
                        timestamp = logEntity.timestamp,
                        severity = logEntity.severity,
                    )
                }
            }
    }

    override suspend fun addLog(log: Log) {
        dao.saveLog(
            LogEntity(
                tag = log.tag,
                message = log.message,
                timestamp = log.timestamp,
                severity = log.severity,
            )
        )
    }

    override suspend fun clearLogs() {
        dao.clear()
    }
}