package com.leoapps.eggy.root.presentation.model

sealed class RootNavigationCommand {
    object OpenSetupScreen : RootNavigationCommand()
    object OpenProgressScreen : RootNavigationCommand()
}