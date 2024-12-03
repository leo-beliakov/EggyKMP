package com.leoapps.eggy

import android.app.Application
import com.leoapps.eggy.di.initKoin
import org.koin.android.ext.koin.androidContext

class EggyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EggyApplication)
        }
    }
}