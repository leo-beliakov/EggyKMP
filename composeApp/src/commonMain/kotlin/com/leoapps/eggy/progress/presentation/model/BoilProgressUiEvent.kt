package com.leoapps.progress.presentation.model

sealed interface BoilProgressUiEvent {
    object NavigateBack : BoilProgressUiEvent
    object OpenNotificationsSettings : BoilProgressUiEvent
    object OpenLogs : BoilProgressUiEvent
}