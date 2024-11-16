package com.leoapps.eggy

import androidx.compose.ui.window.ComposeUIViewController
import com.leoapps.eggy.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }