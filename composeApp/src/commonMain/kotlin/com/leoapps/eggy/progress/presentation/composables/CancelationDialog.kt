package com.leoapps.progress.presentation.composables

import androidx.compose.runtime.Composable
import com.leoapps.base_ui.components.dialog.Dialog
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.common_no
import eggy.composeapp.generated.resources.common_yes
import eggy.composeapp.generated.resources.progress_cancelation_dialog_subtitle
import eggy.composeapp.generated.resources.progress_cancelation_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun CancelationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    title = stringResource(Res.string.progress_cancelation_dialog_title),
    subtitle = stringResource(Res.string.progress_cancelation_dialog_subtitle),
    positiveButtonText = stringResource(Res.string.common_yes),
    dismissButtonText = stringResource(Res.string.common_no),
    onPositiveButtonClicked = onConfirm,
    onDismissButtonClicked = onDismiss,
)
