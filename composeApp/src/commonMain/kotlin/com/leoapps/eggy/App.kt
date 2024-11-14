package com.leoapps.eggy

import androidx.compose.runtime.Composable
import com.leoapps.eggy.base.ui.theme.EggyTheme
import com.leoapps.eggy.root.presentation.RootScreen
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.eggy.welcome.presentation.WelcomeScreen
import com.leoapps.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.setup.presentation.BoilSetupViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    EggyTheme {
        RootScreen()
    }
}