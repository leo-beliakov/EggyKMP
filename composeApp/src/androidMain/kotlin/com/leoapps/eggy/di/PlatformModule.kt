package com.leoapps.eggy.di

import com.leoapps.eggy.vibration.VibrationManagerImpl
import com.leoapps.eggy.vibration.domain.VibrationManager
import com.leoapps.progress.presentation.BoilProgressViewModel
import com.leoapps.setup.presentation.BoilSetupViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::VibrationManagerImpl).bind(VibrationManager::class)

    viewModelOf(::BoilSetupViewModel)
    viewModelOf(::BoilProgressViewModel)
}