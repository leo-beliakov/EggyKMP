package com.leoapps.eggy.logs.domain.model

import com.leoapps.eggy.logs.data.model.LogSeverity

data class Log(
    val tag: String,
    val message: String,
    val timestamp: Long,
    val severity: LogSeverity
)
