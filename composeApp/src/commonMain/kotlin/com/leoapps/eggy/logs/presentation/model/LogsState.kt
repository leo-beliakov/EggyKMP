package com.leoapps.eggy.logs.presentation.model

import com.leoapps.eggy.logs.domain.model.Log

data class LogsState(
    val logs: List<Log> = emptyList(),
    val fakeTimerEnabled: Boolean = false,
)
