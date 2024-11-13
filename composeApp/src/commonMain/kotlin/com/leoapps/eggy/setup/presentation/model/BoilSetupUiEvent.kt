package com.leoapps.setup.presentation.model

import com.leoapps.base.egg.domain.model.EggBoilingType


sealed interface BoilSetupUiEvent {

    data class OpenProgressScreen(
        val eggType: EggBoilingType,
        val calculatedTime: Long,
    ) : BoilSetupUiEvent
}