package com.leoapps.eggy.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.leoapps.eggy.base.database.data.EggyDatabase
import com.leoapps.eggy.base.notification.BoilProgressNotificationManager
import com.leoapps.eggy.base.notification.NotificationChannelsManager
import com.leoapps.eggy.base.storage.ApplicationDirectoryProvider
import com.leoapps.eggy.base.storage.ApplicationDirectoryProviderAndroidImpl
import com.leoapps.eggy.common.vibration.VibrationManagerAndroidImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.timer.TimerManager
import com.leoapps.eggy.timer.TimerManagerAndroidImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val platformModule = module {
    singleOf(::VibrationManagerAndroidImpl).bind(VibrationManager::class)
    singleOf(::TimerManagerAndroidImpl).bind(TimerManager::class)

    factoryOf(::BoilProgressNotificationManager).bind(NotificationChannelsManager::class)
    factoryOf(::ApplicationDirectoryProviderAndroidImpl).bind(ApplicationDirectoryProvider::class)

    // Database region
    single {
        val context = get<Context>()
        Room.databaseBuilder<EggyDatabase>(
            context = context,
            name = context.getDatabasePath(EggyDatabase.DATABASE_NAME).absolutePath
        ).setDriver(BundledSQLiteDriver())
            .build()
    }.bind(EggyDatabase::class)
}