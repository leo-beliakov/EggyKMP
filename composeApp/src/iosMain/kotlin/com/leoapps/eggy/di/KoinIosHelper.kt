package com.leoapps.eggy.di

import com.leoapps.eggy.timer.LiveActivityManager
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin

object KoinIosHelper {

    private lateinit var koin: Koin

    fun initKoin(
        createLiveActivityManager: () -> LiveActivityManager
    ) {
        val koinApp = com.leoapps.eggy.di.initKoin() {
            modules(
                platformModule(
                    createLiveActivityManager = createLiveActivityManager
                )
            )
        }

        koin = koinApp.koin
    }

    @OptIn(BetaInteropApi::class)
    fun get(objCProtocol: ObjCProtocol): Any {
        val kClazz = getOriginalKotlinClass(objCProtocol)!!
        return koin.get(kClazz, null, null)
    }
}
