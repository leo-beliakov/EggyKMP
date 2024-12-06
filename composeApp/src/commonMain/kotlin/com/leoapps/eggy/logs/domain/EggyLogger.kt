package com.leoapps.eggy.logs.domain

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter

class EggyLogger(
    logDatabaseWritter: LogDatabaseWriter,
) : Logger(
    config = StaticConfig(
        minSeverity = Severity.Verbose,
        logWriterList = listOf(
            logDatabaseWritter,
            platformLogWriter(),
        )
    ),
    tag = tag,
)