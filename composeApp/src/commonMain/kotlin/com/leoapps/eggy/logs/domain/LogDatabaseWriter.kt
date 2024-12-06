package com.leoapps.eggy.logs.domain

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.leoapps.eggy.logs.data.LogDao
import com.leoapps.eggy.logs.data.model.LogEntity
import com.leoapps.eggy.logs.data.model.mapToLogSeverity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class LogDatabaseWriter(
    private val logsDao: LogDao
) : LogWriter() {

    private val coroutineScope = CoroutineScope(SupervisorJob())

    override fun log(
        severity: Severity,
        message: String,
        tag: String,
        throwable: Throwable?
    ) {
        coroutineScope.launch {
            logsDao.saveLog(
                LogEntity(
                    tag = tag,
                    message = message,
                    timestamp = Clock.System.now().toEpochMilliseconds(),
                    severity = severity.mapToLogSeverity()
                )
            )
        }
    }
}
