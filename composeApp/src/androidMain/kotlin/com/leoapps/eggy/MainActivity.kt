package com.leoapps.eggy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.leoapps.eggy.base.startup.AppStartupInformationProvider
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val appStartupInformationProvider: AppStartupInformationProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateAppStartupInformation()

        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    private fun updateAppStartupInformation() {
        appStartupInformationProvider.isLaunchedFromNotification = intent.getBooleanExtra(
            IS_LAUNCHED_FROM_NOTIFICATION_KEY,
            false
        )
    }

    companion object {
        const val IS_LAUNCHED_FROM_NOTIFICATION_KEY = "is_launched_from_notification_key"
    }
}
