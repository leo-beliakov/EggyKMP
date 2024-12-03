package com.leoapps.setup.presentation.model

import com.leoapps.eggy.base.utils.EMPTY_CALCULATED_TIME

data class BoilSetupUiState(
    val availableSizes: List<EggSizeUi> = EggSizeUi.entries,
    val selectedSize: EggSizeUi? = null,
    val availableTypes: List<EggBoilingTypeUi> = EggBoilingTypeUi.entries,
    val selectedType: EggBoilingTypeUi? = null,
    val availableTemperatures: List<EggTemperatureUi> = EggTemperatureUi.entries,
    val selectedTemperature: EggTemperatureUi? = null,
    val calculatedTime: Long = 0L,
    val calculatedTimeText: String = EMPTY_CALCULATED_TIME,
    val nextButtonEnabled: Boolean = false,
)
