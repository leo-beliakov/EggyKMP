package com.leoapps.eggy.progress.domain.model

import com.leoapps.base.egg.domain.model.EggBoilingType

sealed interface TimerStatusUpdate {
    object Finished : TimerStatusUpdate
    object Canceled : TimerStatusUpdate

    data class Progress(
        val timePassedMs: Long,
    ) : TimerStatusUpdate
}
