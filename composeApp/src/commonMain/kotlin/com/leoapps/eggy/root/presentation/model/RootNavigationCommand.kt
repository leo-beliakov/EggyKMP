package com.leoapps.eggy.root.presentation.model

import com.leoapps.base.egg.domain.model.EggBoilingType

sealed class RootNavigationCommand {
    object OpenSetupScreen : RootNavigationCommand()

    data class OpenProgressScreen(
        val boilingTime: Long,
        val eggType: EggBoilingType,
    ) : RootNavigationCommand()
}