package com.leoapps.setup.presentation.model

import com.leoapps.base.egg.domain.model.EggTemperature
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.settings_temp_fridge_title
import eggy.composeapp.generated.resources.settings_temp_room_title
import org.jetbrains.compose.resources.StringResource

enum class EggTemperatureUi(
    val temperature: EggTemperature,
    val titleRes: StringResource,
) {
    ROOM(
        temperature = EggTemperature.ROOM,
        titleRes = Res.string.settings_temp_room_title
    ),
    FRIDGE(
        temperature = EggTemperature.FRIDGE,
        titleRes = Res.string.settings_temp_fridge_title
    )
}