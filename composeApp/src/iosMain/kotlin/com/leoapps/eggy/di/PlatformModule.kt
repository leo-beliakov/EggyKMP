package com.leoapps.eggy.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.leoapps.eggy.base.database.data.EggyDatabase
import com.leoapps.eggy.base.storage.ApplicationDirectoryProvider
import com.leoapps.eggy.base.storage.ApplicationDirectoryProviderIosImpl
import com.leoapps.eggy.timer.TimerManager
import com.leoapps.eggy.timer.TimerManagerIosImpl
import com.leoapps.eggy.common.vibration.domain.VibrationManager
import com.leoapps.eggy.common.vibrator.VibrationManagerIosImpl
import com.leoapps.eggy.timer.LiveActivityManager
import com.leoapps.eggy.timer.NotificationsManager
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


@OptIn(ExperimentalForeignApi::class)
fun platformModule(
    createLiveActivityManager: () -> LiveActivityManager
) = module {
    single { createLiveActivityManager() }.bind(LiveActivityManager::class)
    singleOf(::VibrationManagerIosImpl).bind(VibrationManager::class)
    singleOf(::TimerManagerIosImpl).bind(TimerManager::class)

    factoryOf(::ApplicationDirectoryProviderIosImpl).bind(ApplicationDirectoryProvider::class)
    factoryOf(::NotificationsManager)

    //Database region
    single {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        val dbFilePath = "${documentDirectory?.path}/${EggyDatabase.DATABASE_NAME}"

        Room.databaseBuilder<EggyDatabase>(
            name = dbFilePath,
        ).setDriver(BundledSQLiteDriver())
            .build()
    }.bind(EggyDatabase::class)
}