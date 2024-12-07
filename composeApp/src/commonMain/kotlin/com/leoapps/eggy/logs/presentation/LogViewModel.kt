package com.leoapps.eggy.logs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leoapps.eggy.logs.domain.LogsRepository
import com.leoapps.eggy.logs.presentation.model.LogsState
import com.leoapps.eggy.setup.domain.TimeCalculationSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogsViewModel(
    private val timeCalculationSettingsRepository: TimeCalculationSettingsRepository,
    private val logsRepository: LogsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LogsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(fakeTimerEnabled = timeCalculationSettingsRepository.shouldUseFakeTime())
            }
        }
        logsRepository.getLogs()
            .onEach { logs ->
                _state.update { it.copy(logs) }
            }.launchIn(viewModelScope)
    }

    fun onClearLogsClicked() {
        viewModelScope.launch {
            logsRepository.clearLogs()
        }
    }

    fun onFakeTimerToggle() {
        viewModelScope.launch {
            val fakeTimerEnabled = state.value.fakeTimerEnabled
            timeCalculationSettingsRepository.setShouldUseFakeTime(!fakeTimerEnabled)
            _state.update {
                it.copy(fakeTimerEnabled = !fakeTimerEnabled)
            }
        }
    }

    fun onCopyClicked() {

    }
}

