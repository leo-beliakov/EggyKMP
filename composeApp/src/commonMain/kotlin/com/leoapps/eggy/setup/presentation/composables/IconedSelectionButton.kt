package com.leoapps.setup.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leoapps.eggy.base.ui.theme.EggyTheme
import com.leoapps.eggy.base.ui.theme.GrayLight
import com.leoapps.eggy.base.ui.theme.Primary
import com.leoapps.eggy.base.ui.theme.PrimaryLight
import com.leoapps.eggy.base.ui.theme.dimens
import com.leoapps.vibration.presentation.LocalVibrationManager
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.egg_soft
import eggy.composeapp.generated.resources.settings_size_l
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IconedSelectionButton(
    iconRes: DrawableResource,
    titleRes: StringResource,
    subtitleRes: StringResource,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vibratorManager = LocalVibrationManager.current
    val selectionColor = remember(selected) { if (selected) Primary else GrayLight }

    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.spaceL)
                .fillMaxWidth()
                .sizeIn(minHeight = MaterialTheme.dimens.selectableIconedButtonHeight)
                .border(
                    width = 1.dp,
                    color = selectionColor,
                    shape = RoundedCornerShape(MaterialTheme.dimens.cornerS)
                )
                .clip(RoundedCornerShape(MaterialTheme.dimens.cornerS))
                .toggleable(
                    value = selected,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = PrimaryLight),
                    onValueChange = {
                        vibratorManager.vibrateOnClick()
                        onClick()
                    }
                )
                .padding(MaterialTheme.dimens.paddingL)
        ) {
            Text(
                text = stringResource(titleRes),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = selectionColor
            )
            Text(
                text = stringResource(subtitleRes),
                style = MaterialTheme.typography.bodyLarge,
                color = selectionColor
            )
        }
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(0.6f)
        )
    }
}

@Preview
@Composable
private fun IconedSelectionButtonPreview() {
    EggyTheme {
        IconedSelectionButton(
            iconRes = Res.drawable.egg_soft,
            titleRes = Res.string.settings_size_l,
            subtitleRes = Res.string.settings_size_l,
            selected = false,
            onClick = { }
        )
    }
}