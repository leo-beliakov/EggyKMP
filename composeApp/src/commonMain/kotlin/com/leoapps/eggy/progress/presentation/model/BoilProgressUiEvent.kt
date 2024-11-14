package com.leoapps.progress.presentation.model

sealed interface BoilProgressUiEvent {
    object NavigateBack : BoilProgressUiEvent
    object RequestNotificationsPermission : BoilProgressUiEvent
    object OpenNotificationsSettings : BoilProgressUiEvent
}