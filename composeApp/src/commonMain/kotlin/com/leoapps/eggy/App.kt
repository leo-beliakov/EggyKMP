package com.leoapps.eggy

import androidx.compose.runtime.Composable
import com.leoapps.eggy.base.ui.theme.EggyTheme
import com.leoapps.eggy.welcome.presentation.WelcomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    EggyTheme {
        WelcomeScreen(
            onContinueClicked = { }
        )
    }
}