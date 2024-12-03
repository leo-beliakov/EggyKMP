package com.leoapps.eggy.progress.domain.model

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import kotlinx.datetime.Instant

data class TimerSettings(
    val timerEndTime: Instant,
    val timerTotalTime: Long,
    val eggType: EggBoilingType,
    val eggSize: EggSize,
    val eggTemperature: EggTemperature,
)
