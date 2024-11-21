package com.leoapps.eggy.di

import com.leoapps.eggy.base.egg.domain.TimerPlatformHelper
import com.leoapps.eggy.base.egg.domain.TimerPlatformHelperAndroidImpl
import com.leoapps.eggy.common.vibration.VibrationManagerAndroidImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::VibrationManagerAndroidImpl).bind(VibrationManager::class)
    singleOf(::TimerPlatformHelperAndroidImpl).bind(TimerPlatformHelper::class)
}