package com.leoapps.eggy.progress.domain.model

import com.leoapps.base.egg.domain.model.EggBoilingType
import kotlinx.datetime.Instant

data class TimerSettings(
    val timerEndTime: Instant,
    val eggType: EggBoilingType,
)
