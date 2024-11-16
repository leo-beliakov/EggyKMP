package com.leoapps.eggy.di

import com.leoapps.setup.domain.CalculateBoilingTimeUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val sharedModule = module {
    factoryOf(::CalculateBoilingTimeUseCase)
}