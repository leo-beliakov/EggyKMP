package com.leoapps.progress.presentation.composables

import androidx.compose.runtime.Composable
import com.leoapps.base_ui.components.dialog.Dialog
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.progress_rationale_dialog_go_to_settings
import eggy.composeapp.generated.resources.progress_rationale_dialog_subtitle
import eggy.composeapp.generated.resources.progress_rationale_dialog_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun PermissionOpenSettingsDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) = Dialog(
    title = stringResource(Res.string.progress_rationale_dialog_title),
    subtitle = stringResource(Res.string.progress_rationale_dialog_subtitle),
    positiveButtonText = stringResource(Res.string.progress_rationale_dialog_go_to_settings),
    onPositiveButtonClicked = onConfirm,
    onDismissButtonClicked = onDismiss,
)