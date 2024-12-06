package com.leoapps.eggy.logs.data.model

import co.touchlab.kermit.Severity

enum class LogSeverity {
    VERBOSE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    ASSERT,
}

fun Severity.mapToLogSeverity(): LogSeverity {
    return when (this) {
        Severity.Verbose -> LogSeverity.VERBOSE
        Severity.Debug -> LogSeverity.DEBUG
        Severity.Info -> LogSeverity.INFO
        Severity.Warn -> LogSeverity.WARN
        Severity.Error -> LogSeverity.ERROR
        Severity.Assert -> LogSeverity.ASSERT
    }
}
