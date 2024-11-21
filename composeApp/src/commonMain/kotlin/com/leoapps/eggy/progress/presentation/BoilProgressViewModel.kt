package com.leoapps.progress.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.base.egg.domain.TimerInteractor
import com.leoapps.eggy.common.permissions.model.PermissionStatus
import com.leoapps.eggy.common.utils.convertMsToTimerText
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.progress.domain.model.TimerStatusUpdate
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import com.leoapps.progress.presentation.model.ActionButtonState
import com.leoapps.progress.presentation.model.BoilProgressUiEvent
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.common_hard_boiled_eggs
import eggy.composeapp.generated.resources.common_medium_boiled_eggs
import eggy.composeapp.generated.resources.common_soft_boiled_eggs
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable // https://issuetracker.google.com/issues/280284177
class BoilProgressViewModel(
    private val vibrationManager: VibrationManager,
    private val timerInteractor: TimerInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<BoilProgressScreenDestination>()
    private val eggType = EggBoilingType.fromString(args.type) ?: EggBoilingType.MEDIUM
    private val boilingTime = args.calculatedTime

    private val _state = MutableStateFlow(getInitialState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<BoilProgressUiEvent>()
    val events = _events.asSharedFlow()

    //    private var binder: BoilProgressService.MyBinder? = null
    private var serviceSubscribtionJob: Job? = null

//    private val serviceConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//            binder = service as? BoilProgressService.MyBinder
//            serviceSubscribtionJob = collectServiceState()
//        }

//        override fun onServiceDisconnected(name: ComponentName?) {
//            binder = null
//            serviceSubscribtionJob?.cancel()
//        }
//    }

    init {
        timerInteractor.timerUpdates
            .onEach { timerState ->
                when (timerState) {
                    TimerStatusUpdate.Idle -> { }
                    TimerStatusUpdate.Canceled -> onTimerCanceled()
                    TimerStatusUpdate.Finished -> onTimerFinished()
                    is TimerStatusUpdate.Progress -> onTimerProgressUpdate(timerState)
                }
            }.launchIn(viewModelScope)
    }

    fun onButtonClicked() {
        when (state.value.buttonState) {
            ActionButtonState.START -> onStartClicked()
            ActionButtonState.STOP -> onStopClicked()
        }
    }

    fun onBackClicked() {
        if (state.value.isInProgress) {
            showDoalog(BoilProgressUiState.Dialog.CANCELATION)
        } else {
            viewModelScope.launch {
                _events.emit(BoilProgressUiEvent.NavigateBack)
            }
        }
    }

    fun onCancelationDialogDismissed() {
        showDoalog(null)
    }

    fun onCancelationDialogConfirmed() {
        showDoalog(null)
        viewModelScope.launch { _events.emit(BoilProgressUiEvent.NavigateBack) }
        timerInteractor.stopTimer()
    }

    fun onCelebrationFinished() {
//        _state.update {
//            it.copy(finishCelebrationConfig = null)
//        }
    }

    fun onPermissionResult(result: PermissionStatus) {
        when (result) {
            PermissionStatus.GRANTED -> {
                timerInteractor.startTimer(boilingTime, eggType)
                _state.update { it.copy(buttonState = ActionButtonState.STOP) }
            }

            PermissionStatus.DENIED -> {
                showDoalog(BoilProgressUiState.Dialog.RATIONALE)
            }

            PermissionStatus.DONT_ASK_AGAIN -> {
                showDoalog(BoilProgressUiState.Dialog.RATIONALE_GO_TO_SETTINGS)
            }
        }
    }

    fun onPermissionSettingsResult(result: PermissionStatus) {
        when (result) {
            PermissionStatus.GRANTED -> {
                showDoalog(null)
                timerInteractor.startTimer(boilingTime, eggType)
                _state.update { it.copy(buttonState = ActionButtonState.STOP) }
            }

            PermissionStatus.DENIED,
            PermissionStatus.DONT_ASK_AGAIN -> {
                showDoalog(null)
                // think about this case
            }
        }
    }

    fun onRationaleDialogConfirm() {
        showDoalog(null)
        viewModelScope.launch {
            _events.emit(BoilProgressUiEvent.RequestNotificationsPermission)
        }
    }

    fun onGoToSettingsDialogConfirm() {
        showDoalog(null)
        viewModelScope.launch {
            _events.emit(BoilProgressUiEvent.OpenNotificationsSettings)
        }
    }

    private fun getInitialState(): BoilProgressUiState {
        return BoilProgressUiState(
            boilingTime = convertMsToTimerText(boilingTime),
            titleRes = when (eggType) {
                EggBoilingType.SOFT -> Res.string.common_soft_boiled_eggs
                EggBoilingType.MEDIUM -> Res.string.common_medium_boiled_eggs
                EggBoilingType.HARD -> Res.string.common_hard_boiled_eggs
            }
        )
    }

    private fun onTimerCanceled() {
        _state.update {
            it.copy(
                progress = 0f,
                progressText = convertMsToTimerText(0L),
                buttonState = ActionButtonState.START
            )
        }
    }

    private fun onTimerFinished() {
        _state.update {
            it.copy(
                progress = 0f,
                progressText = convertMsToTimerText(0L),
                buttonState = ActionButtonState.START,
//                finishCelebrationConfig = getCelebrationConfig()
            )
        }
        vibrationManager.vibratePattern(
            pattern = TIMER_FINISH_VIBRARTION_PATTERN
        )
    }

    private fun onTimerProgressUpdate(timerState: TimerStatusUpdate.Progress) {
        _state.update {
            it.copy(
                progress = timerState.valueMs / boilingTime.toFloat(),
                progressText = convertMsToTimerText(timerState.valueMs),
            )
        }
    }

    private fun showDoalog(dialog: BoilProgressUiState.Dialog?) {
        _state.update {
            it.copy(selectedDialog = dialog)
        }
    }

    private fun onStartClicked() {
        timerInteractor.startTimer(boilingTime, eggType)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            viewModelScope.launch {
//                _events.emit(BoilProgressUiEvent.RequestNotificationsPermission)
//            }
//        } else {
//            onPermissionResult(PermissionStatus.GRANTED)
//        }
    }

    private fun onStopClicked() {
        timerInteractor.stopTimer()
        _state.update { it.copy(buttonState = ActionButtonState.START) }
    }

//    private fun getCelebrationConfig(): List<Party> {
//        val party = Party(
//            speed = 10f,
//            maxSpeed = 30f,
//            damping = 0.9f,
//            angle = Angle.RIGHT - 45,
//            spread = Spread.SMALL,
//            colors = listOf(
//                СonfettiYellow.toArgb(),
//                СonfettiOrange.toArgb(),
//                СonfettiPurple.toArgb(),
//                СonfettiPink.toArgb(),
//            ),
//            emitter = Emitter(TIMER_FINISH_ANIMATION_DURATION_MS).perSecond(50),
//            position = Position.Relative(0.0, 0.35)
//        )
//
//        return listOf(
//            party,
//            party.copy(
//                angle = party.angle - 90, // flip angle from right to left
//                position = Position.Relative(1.0, 0.35)
//            ),
//        )
//    }

    private companion object {
        val TIMER_FINISH_VIBRARTION_PATTERN = longArrayOf(0, 200, 100, 300, 400, 500)
        val TIMER_FINISH_ANIMATION_DURATION_MS = 3000L
    }
}
