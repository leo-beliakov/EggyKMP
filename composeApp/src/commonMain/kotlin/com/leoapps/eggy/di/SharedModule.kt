package com.leoapps.eggy.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.leoapps.eggy.base.database.data.EggyDatabase
import com.leoapps.eggy.base.startup.AppStartupInformationProvider
import com.leoapps.eggy.base.startup.AppStartupInformationProviderImpl
import com.leoapps.eggy.base.storage.ApplicationDirectoryProvider
import com.leoapps.eggy.logs.data.LogDao
import com.leoapps.eggy.logs.domain.EggyLogger
import com.leoapps.eggy.logs.domain.LogDatabaseWriter
import com.leoapps.eggy.progress.data.TimerSettingsRepositoryImpl
import com.leoapps.eggy.progress.domain.TimerSettingsRepository
import com.leoapps.eggy.root.presentation.RootViewModel
import com.leoapps.progress.presentation.BoilProgressViewModel
import com.leoapps.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.setup.presentation.BoilSetupViewModel
import okio.Path.Companion.toPath
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

private const val dataStoreFileName = "eggy.preferences_pb"

val sharedModule = module {
    single {
        val appDirectoryProvider = get<ApplicationDirectoryProvider>()
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { "${appDirectoryProvider.appDirectory}/$dataStoreFileName".toPath() }
        )
    }

    singleOf(::AppStartupInformationProviderImpl).bind(AppStartupInformationProvider::class)

    factoryOf(::CalculateBoilingTimeUseCase)
    factoryOf(::TimerSettingsRepositoryImpl).bind(TimerSettingsRepository::class)
    factory { get<EggyDatabase>().logDao() }.bind(LogDao::class)
    factoryOf(::LogDatabaseWriter)
    factoryOf(::EggyLogger)

    viewModelOf(::RootViewModel)
    viewModelOf(::BoilSetupViewModel)
    viewModelOf(::BoilProgressViewModel)
}