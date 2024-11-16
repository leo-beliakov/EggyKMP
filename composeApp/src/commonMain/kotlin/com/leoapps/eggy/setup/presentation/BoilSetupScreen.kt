package com.leoapps.eggy.welcome.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base_ui.utils.CollectEventsWithLifecycle
import com.leoapps.base_ui.utils.annotatedStringResource
import com.leoapps.base_ui.utils.toPx
import com.leoapps.eggy.base.ui.theme.EggyTheme
import com.leoapps.eggy.base.ui.theme.GrayLight
import com.leoapps.eggy.base.ui.theme.Primary
import com.leoapps.eggy.base.ui.theme.White
import com.leoapps.eggy.base.ui.theme.dimens
import com.leoapps.setup.presentation.BoilSetupViewModel
import com.leoapps.setup.presentation.composables.CounterComposable
import com.leoapps.setup.presentation.composables.IconedSelectionButton
import com.leoapps.setup.presentation.composables.SelectionButton
import com.leoapps.setup.presentation.model.BoilSetupUiEvent
import com.leoapps.setup.presentation.model.BoilSetupUiState
import com.leoapps.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.setup.presentation.model.EggSizeUi
import com.leoapps.setup.presentation.model.EggTemperatureUi
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.common_continue
import eggy.composeapp.generated.resources.ic_next
import eggy.composeapp.generated.resources.settings_size_title
import eggy.composeapp.generated.resources.settings_temp_subtitle
import eggy.composeapp.generated.resources.settings_temp_title
import eggy.composeapp.generated.resources.settings_type_subtitle
import eggy.composeapp.generated.resources.settings_type_title
import eggy.composeapp.generated.resources.setup_egg_half
import eggy.composeapp.generated.resources.setup_header_subtitle
import eggy.composeapp.generated.resources.setup_header_title
import eggy.composeapp.generated.resources.setup_timer_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BoilSetupScreen(
    viewModel: BoilSetupViewModel = koinViewModel(),
    onContinueClicked: (type: EggBoilingType, time: Long) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoilSetupScreen(
        state = state,
        onNextClicked = viewModel::onNextClicked,
        onSizeSelected = viewModel::onSizeSelected,
        onTypeSelected = viewModel::onTypeSelected,
        onTemperatureSelected = viewModel::onTemperatureSelected,
    )

    CollectEventsWithLifecycle(viewModel.events) { event ->
        when (event) {
            is BoilSetupUiEvent.OpenProgressScreen -> onContinueClicked(
                event.eggType,
                event.calculatedTime
            )
        }
    }
}

@Composable
private fun BoilSetupScreen(
    state: BoilSetupUiState,
    onNextClicked: () -> Unit,
    onSizeSelected: (EggSizeUi) -> Unit,
    onTypeSelected: (EggBoilingTypeUi) -> Unit,
    onTemperatureSelected: (EggTemperatureUi) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceXL),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                vertical = MaterialTheme.dimens.screenPaddingXL,
                horizontal = MaterialTheme.dimens.screenPaddingL,
            )
    ) {
        HeaderSection()
        TemperatureSection(
            temperatures = state.availableTemperatures,
            selectedTemperature = state.selectedTemperature,
            onTemperatureSelected = onTemperatureSelected,
        )
        SizeSection(
            sizes = state.availableSizes,
            selectedSize = state.selectedSize,
            onSizeSelected = onSizeSelected,
        )
        BoiledTypeSection(
            types = state.availableTypes,
            selectedType = state.selectedType,
            onTypeSelected = onTypeSelected,
        )
        Spacer(
            modifier = Modifier.weight(1f, true)
        )
        TimerSection(
            calculatedTime = state.calculatedTimeText,
            nextButtonEnabled = state.nextButtonEnabled,
            onNextClicked = onNextClicked,
        )
    }
}

@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceXS),
            modifier = Modifier
                .weight(1f, true)
                .padding(top = MaterialTheme.dimens.spaceM)
        ) {
            Text(
                text = annotatedStringResource(Res.string.setup_header_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(Res.string.setup_header_subtitle),
                style = MaterialTheme.typography.titleSmall,
                color = GrayLight,
            )
        }
        Image(
            painter = painterResource(Res.drawable.setup_egg_half),
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer(translationX = MaterialTheme.dimens.screenPaddingL.toPx())
                .height(120.dp)
        )
    }
}

@Composable
private fun TemperatureSection(
    temperatures: List<EggTemperatureUi>,
    selectedTemperature: EggTemperatureUi?,
    onTemperatureSelected: (EggTemperatureUi) -> Unit,
) {
    Column {
        Text(
            text = annotatedStringResource(Res.string.settings_temp_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceS)
        ) {
            temperatures.forEach { temperature ->
                SelectionButton(
                    titleRes = temperature.titleRes,
                    subtitleRes = Res.string.settings_temp_subtitle,
                    selected = temperature == selectedTemperature,
                    onClick = { onTemperatureSelected(temperature) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SizeSection(
    sizes: List<EggSizeUi>,
    selectedSize: EggSizeUi?,
    onSizeSelected: (EggSizeUi) -> Unit
) {
    Column {
        Text(
            text = annotatedStringResource(Res.string.settings_size_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceS)
        ) {
            sizes.forEach { size ->
                SelectionButton(
                    titleRes = size.titleRes,
                    selected = size == selectedSize,
                    onClick = { onSizeSelected(size) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BoiledTypeSection(
    types: List<EggBoilingTypeUi>,
    selectedType: EggBoilingTypeUi?,
    onTypeSelected: (EggBoilingTypeUi) -> Unit
) {
    Column {
        Text(
            text = annotatedStringResource(Res.string.settings_type_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
            modifier = Modifier.padding(top = MaterialTheme.dimens.spaceXS)
        ) {
            types.forEach { type ->
                IconedSelectionButton(
                    iconRes = type.iconRes,
                    titleRes = type.titleRes,
                    subtitleRes = Res.string.settings_type_subtitle,
                    selected = type == selectedType,
                    onClick = { onTypeSelected(type) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TimerSection(
    calculatedTime: String,
    nextButtonEnabled: Boolean,
    onNextClicked: () -> Unit
) {
//    val vibratorManager = LocalVibrationManager.current

    Row {
        Column(
            modifier = Modifier.weight(1f, true)
        ) {
            Text(
                text = stringResource(Res.string.setup_timer_title),
                style = MaterialTheme.typography.titleSmall,
                color = GrayLight,
            )
            CounterComposable(
                currentTimeText = calculatedTime
            )
        }
        ElevatedButton(
            enabled = nextButtonEnabled,
            shape = RoundedCornerShape(MaterialTheme.dimens.cornerM),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            onClick = {
//                vibratorManager.vibrateOnClick()
                onNextClicked()
            },
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = MaterialTheme.dimens.elevationM,
                pressedElevation = MaterialTheme.dimens.elevationL,
            ),
            modifier = Modifier
                .testTag("buttonContinue")
                .size(MaterialTheme.dimens.buttonHeight)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_next),
                tint = White,
                contentDescription = stringResource(Res.string.common_continue)
            )
        }
    }
}

@Preview
@Composable
private fun BoilSetupScreenPreview() {
    EggyTheme {
        BoilSetupScreen(
            onContinueClicked = { _, _ -> }
        )
    }
}
