package com.leoapps.eggy.di

import com.leoapps.eggy.base.egg.domain.TimerManager
import com.leoapps.eggy.timer.TimerManagerAndroidImpl
import com.leoapps.eggy.base.notification.BoilProgressNotificationManager
import com.leoapps.eggy.base.notification.NotificationChannelsManager
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
}