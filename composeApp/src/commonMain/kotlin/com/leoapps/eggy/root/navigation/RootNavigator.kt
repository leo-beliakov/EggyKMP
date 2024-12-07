package com.leoapps.eggy.root.navigation

import androidx.navigation.NavController
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.root.presentation.BoilProgressScreenDestination
import com.leoapps.eggy.root.presentation.BoilSetupScreenDestination
import com.leoapps.eggy.root.presentation.LogsScreenDestination
import com.leoapps.eggy.root.presentation.WelcomeScreenDestination

class RootNavigator(
    private val navController: NavController,
) {
    fun openSetupScreen() {
        navController.navigate(BoilSetupScreenDestination) {
            popUpTo<WelcomeScreenDestination> {
                inclusive = true
            }
        }
    }

    fun openProgressScreen(
        type: EggBoilingType,
        time: Long,
    ) {
        navController.navigate(
            BoilProgressScreenDestination(
                type = type.toString(),
                calculatedTime = time,
            )
        )
    }

    fun jumpToProgressScreen(
        type: EggBoilingType,
        time: Long,
    ) {
        openSetupScreen()
        openProgressScreen(type = type, time = time)
    }

    fun navigateBack() {
        navController.navigateUp()
    }

    fun openLogsScreen() {
        navController.navigate(LogsScreenDestination)
    }
}