package com.leoapps.eggy.progress.domain.model

sealed interface TimerStatusUpdate {
    object Idle : TimerStatusUpdate
    object Finished : TimerStatusUpdate
    object Canceled : TimerStatusUpdate
    data class Progress(
        val valueMs: Long
    ) : TimerStatusUpdate
}
