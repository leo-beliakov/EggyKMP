package com.leoapps.setup.presentation.model

import com.leoapps.base.egg.domain.model.EggBoilingType
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.egg_hard
import eggy.composeapp.generated.resources.egg_medium
import eggy.composeapp.generated.resources.egg_soft
import eggy.composeapp.generated.resources.settings_type_hard_title
import eggy.composeapp.generated.resources.settings_type_medium_title
import eggy.composeapp.generated.resources.settings_type_soft_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class EggBoilingTypeUi(
    val type: EggBoilingType,
    val titleRes: StringResource,
    val iconRes: DrawableResource,
) {
    SOFT(
        type = EggBoilingType.SOFT,
        titleRes = Res.string.settings_type_soft_title,
        iconRes = Res.drawable.egg_soft,
    ),
    MEDIUM(
        type = EggBoilingType.MEDIUM,
        titleRes = Res.string.settings_type_medium_title,
        iconRes = Res.drawable.egg_medium,
    ),
    HARD(
        type = EggBoilingType.HARD,
        titleRes = Res.string.settings_type_hard_title,
        iconRes = Res.drawable.egg_hard,
    ),
}