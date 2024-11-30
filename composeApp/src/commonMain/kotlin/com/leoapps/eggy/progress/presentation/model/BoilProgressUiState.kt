package com.leoapps.eggy.setup.presentation.model

import com.leoapps.progress.presentation.model.ActionButtonState
import org.jetbrains.compose.resources.StringResource

data class BoilProgressUiState(
    val titleRes: StringResource,
    val progress: Float = 0f,
    val progressText: String = "",
    val boilingTime: String = "",
    val buttonState: ActionButtonState = ActionButtonState.START,
//    val finishCelebrationConfig: List<Party>? = null,
    val selectedDialog: Dialog? = null,
) {
    val isInProgress: Boolean
        get() = buttonState == ActionButtonState.STOP

    enum class Dialog {
        CANCELATION,
        RATIONALE,
        RATIONALE_GO_TO_SETTINGS,
    }
}

