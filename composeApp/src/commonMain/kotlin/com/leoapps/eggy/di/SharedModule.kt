package com.leoapps.eggy.di

import com.leoapps.eggy.root.presentation.RootViewModel
import com.leoapps.progress.presentation.BoilProgressViewModel
import com.leoapps.setup.domain.CalculateBoilingTimeUseCase
import com.leoapps.setup.presentation.BoilSetupViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {
    factoryOf(::CalculateBoilingTimeUseCase)

    viewModelOf(::RootViewModel)
    viewModelOf(::BoilSetupViewModel)
    viewModelOf(::BoilProgressViewModel)
}