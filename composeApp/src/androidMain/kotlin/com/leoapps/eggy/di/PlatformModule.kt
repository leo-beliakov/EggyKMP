package com.leoapps.eggy.di

import com.leoapps.eggy.base.egg.domain.TimerHelper
import com.leoapps.eggy.timer.TimerHelperAndroidImpl
import com.leoapps.eggy.base.notification.BoilProgressNotificationManager
import com.leoapps.eggy.base.notification.NotificationChannelsManager
import com.leoapps.eggy.common.vibration.VibrationManagerAndroidImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::VibrationManagerAndroidImpl).bind(VibrationManager::class)
    singleOf(::TimerHelperAndroidImpl).bind(TimerHelper::class)
    factoryOf(::BoilProgressNotificationManager).bind(NotificationChannelsManager::class)
}