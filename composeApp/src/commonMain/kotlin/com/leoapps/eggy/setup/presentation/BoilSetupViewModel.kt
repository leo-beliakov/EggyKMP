package com.leoapps.setup.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.common.utils.toFormattedTime
import com.leoapps.eggy.logs.domain.EggyLogger
import com.leoapps.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.setup.presentation.model.BoilSetupUiEvent
import com.leoapps.setup.presentation.model.BoilSetupUiState
import com.leoapps.setup.presentation.model.EggBoilingTypeUi
import com.leoapps.setup.presentation.model.EggSizeUi
import com.leoapps.setup.presentation.model.EggTemperatureUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable // https://issuetracker.google.com/issues/280284177
class BoilSetupViewModel(
    private val calculateBoilingTime: CalculateBoilingTimeUseCase,
    private val logger: EggyLogger,
) : ViewModel() {

    private val _state = MutableStateFlow(BoilSetupUiState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<BoilSetupUiEvent>()
    val events = _events.asSharedFlow()

    init {
        logger.d { "BoilSetupViewModel init" }
        state.distinctUntilChanged { old, new ->
            old.selectedTemperature == new.selectedTemperature &&
                    old.selectedSize == new.selectedSize &&
                    old.selectedType == new.selectedType
        }.onEach {
            _state.update {
                val time = calculateBoilingTime(
                    temperature = it.selectedTemperature?.temperature,
                    size = it.selectedSize?.size,
                    type = it.selectedType?.type,
                )

                it.copy(
                    calculatedTime = time,
                    calculatedTimeText = time.toFormattedTime(),
                    nextButtonEnabled = it.selectedTemperature != null &&
                            it.selectedSize != null &&
                            it.selectedType != null
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onSizeSelected(eggSize: EggSizeUi) {
        _state.update {
            it.copy(selectedSize = eggSize)
        }
    }

    fun onTypeSelected(eggBoilingType: EggBoilingTypeUi) {
        _state.update {
            it.copy(selectedType = eggBoilingType)
        }
    }

    fun onTemperatureSelected(eggTemperature: EggTemperatureUi) {
        _state.update {
            it.copy(selectedTemperature = eggTemperature)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            _events.emit(
                BoilSetupUiEvent.OpenProgressScreen(
                    eggType = state.value.selectedType?.type ?: EggBoilingType.MEDIUM,
                    calculatedTime = state.value.calculatedTime
                )
            )
        }
    }
}