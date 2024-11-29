package com.leoapps.eggy

import androidx.compose.ui.window.ComposeUIViewController
import com.leoapps.eggy.di.initKoin
import com.leoapps.eggy.di.platformModule
import com.leoapps.eggy.timer.LiveActivityManager

fun MainViewController(
    activityManagerFactory: () -> LiveActivityManager
) = ComposeUIViewController(
    configure = {
        initKoin(){
            modules(
                platformModule(
                    createLiveActivityManager = activityManagerFactory
                )
            )
        }
    }
) { App() }