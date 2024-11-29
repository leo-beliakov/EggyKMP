package com.leoapps.eggy.di

import com.leoapps.eggy.timer.TimerManager
import com.leoapps.eggy.timer.TimerManagerAndroidImpl
import com.leoapps.eggy.base.notification.BoilProgressNotificationManager
import com.leoapps.eggy.base.notification.NotificationChannelsManager
import com.leoapps.eggy.base.storage.ApplicationDirectoryProvider
import com.leoapps.eggy.base.storage.ApplicationDirectoryProviderAndroidImpl
import com.leoapps.eggy.common.vibration.VibrationManagerAndroidImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val platformModule = module {
    singleOf(::VibrationManagerAndroidImpl).bind(VibrationManager::class)
    singleOf(::TimerManagerAndroidImpl).bind(TimerManager::class)

    factoryOf(::BoilProgressNotificationManager).bind(NotificationChannelsManager::class)
    factoryOf(::ApplicationDirectoryProviderAndroidImpl).bind(ApplicationDirectoryProvider::class)
}