package com.leoapps.eggy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.leoapps.eggy.base.ui.theme.EggyTheme
import com.leoapps.eggy.root.presentation.RootScreen
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.eggy.welcome.presentation.WelcomeScreen
import com.leoapps.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.setup.presentation.BoilSetupViewModel
import com.leoapps.vibration.presentation.LocalVibrationManager
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.context.KoinContext

@Composable
@Preview
fun App() {
    val vibrationManager: VibrationManager = koinInject() //todo remmeber?

    EggyTheme {
        CompositionLocalProvider(LocalVibrationManager provides vibrationManager) {
            RootScreen()
        }
    }
}