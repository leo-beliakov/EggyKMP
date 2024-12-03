package com.leoapps.eggy.di

import com.leoapps.eggy.base.storage.ApplicationDirectoryProvider
import com.leoapps.eggy.base.storage.ApplicationDirectoryProviderIosImpl
import com.leoapps.eggy.timer.TimerManager
import com.leoapps.eggy.timer.TimerManagerIosImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.common.vibrator.VibrationManagerIosImpl
import com.leoapps.eggy.timer.LiveActivityManager
import com.leoapps.eggy.timer.NotificationsManager
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


fun platformModule(
    createLiveActivityManager: () -> LiveActivityManager
) = module {
    single { createLiveActivityManager() }.bind(LiveActivityManager::class)
    singleOf(::VibrationManagerIosImpl).bind(VibrationManager::class)
    singleOf(::TimerManagerIosImpl).bind(TimerManager::class)

    factoryOf(::ApplicationDirectoryProviderIosImpl).bind(ApplicationDirectoryProvider::class)
    factoryOf(::NotificationsManager)
}