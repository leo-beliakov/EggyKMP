package com.leoapps.setup.presentation.model

import com.leoapps.base.egg.domain.model.EggSize
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.settings_size_l
import eggy.composeapp.generated.resources.settings_size_m
import eggy.composeapp.generated.resources.settings_size_s
import org.jetbrains.compose.resources.StringResource

enum class EggSizeUi(
    val size: EggSize,
    val titleRes: StringResource,
) {
    SMALL(
        size = EggSize.SMALL,
        titleRes = Res.string.settings_size_s
    ),
    MEDIUM(
        size = EggSize.MEDIUM,
        titleRes = Res.string.settings_size_m
    ),
    LARGE(
        size = EggSize.LARGE,
        titleRes = Res.string.settings_size_l
    ),
}