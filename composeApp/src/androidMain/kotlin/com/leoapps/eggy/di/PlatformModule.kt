package com.leoapps.eggy.di

import com.leoapps.setup.presentation.BoilSetupViewModel
import com.leoapps.progress.presentation.BoilProgressViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule = module {
    viewModelOf(::BoilSetupViewModel)
    viewModelOf(::BoilProgressViewModel)
}