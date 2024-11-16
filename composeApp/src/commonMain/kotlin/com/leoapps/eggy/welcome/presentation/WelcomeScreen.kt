package com.leoapps.eggy.welcome.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.leoapps.eggy.base.ui.theme.EggyTheme
import com.leoapps.eggy.base.ui.theme.Primary
import com.leoapps.eggy.base.ui.theme.White
import com.leoapps.eggy.base.ui.theme.dimens
import com.leoapps.vibration.presentation.LocalVibrationManager
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.welcome_button_continue
import eggy.composeapp.generated.resources.welcome_egg
import eggy.composeapp.generated.resources.welcome_title_1
import eggy.composeapp.generated.resources.welcome_title_2
import eggy.composeapp.generated.resources.welcome_title_3
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(
    onContinueClicked: () -> Unit
) {
    val vibratorManager = LocalVibrationManager.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(horizontal = MaterialTheme.dimens.screenPaddingL)
    ) {
        val (title1, title2, title3, image, button) = createRefs()
        val titleChain = createVerticalChain(title1, title2, title3, chainStyle = ChainStyle.Packed)
        constrain(titleChain) {
            top.linkTo(parent.top)
            bottom.linkTo(image.top)
        }

        Text(
            text = stringResource(Res.string.welcome_title_1),
            style = MaterialTheme.typography.headlineMedium,
            color = Primary,
            modifier = Modifier.constrainAs(title1) {
                linkTo(start = parent.start, end = parent.end)
            }
        )
        Text(
            text = stringResource(Res.string.welcome_title_2),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(title2) {
                linkTo(start = parent.start, end = parent.end)
            }
        )
        Text(
            text = stringResource(Res.string.welcome_title_3),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.constrainAs(title3) {
                linkTo(start = parent.start, end = parent.end)
            }
        )
        Image(
            painter = painterResource(Res.drawable.welcome_egg),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainAs(image) {
                height = Dimension.percent(0.6f)
                width = Dimension.percent(0.6f)
                linkTo(top = parent.top, bottom = parent.bottom, bias = 0.6f)
                linkTo(start = parent.start, end = parent.end)
            }
        )
        ElevatedButton(
            onClick = {
                vibratorManager.vibrateOnClick()
                onContinueClicked()
            },
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = MaterialTheme.dimens.elevationM,
                pressedElevation = MaterialTheme.dimens.elevationL,
            ),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            modifier = Modifier
                .widthIn(max = MaterialTheme.dimens.buttonMaxWidth)
                .fillMaxWidth()
                .heightIn(min = MaterialTheme.dimens.buttonHeight)
                .constrainAs(button) {
                    top.linkTo(image.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text(
                text = stringResource(Res.string.welcome_button_continue),
                style = MaterialTheme.typography.titleMedium,
                color = White,
                fontWeight = FontWeight.W700,
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    EggyTheme {
        WelcomeScreen(
            onContinueClicked = {}
        )
    }
}