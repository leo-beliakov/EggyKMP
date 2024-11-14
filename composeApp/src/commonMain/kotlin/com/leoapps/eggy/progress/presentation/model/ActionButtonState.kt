package com.leoapps.progress.presentation.model

import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.progress_button_start
import eggy.composeapp.generated.resources.progress_button_stop
import org.jetbrains.compose.resources.StringResource

enum class ActionButtonState(
    val textResId: StringResource,
) {
    START(Res.string.progress_button_start),
    STOP(Res.string.progress_button_stop),
}