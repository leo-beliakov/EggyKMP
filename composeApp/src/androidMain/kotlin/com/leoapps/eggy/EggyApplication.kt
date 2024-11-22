package com.leoapps.eggy

import android.app.Application
import com.leoapps.eggy.base.notification.NotificationChannelsManager
import com.leoapps.eggy.di.initKoin
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class EggyApplication : Application() {

    private val notificationChannelManagers: NotificationChannelsManager by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EggyApplication)
        }

        notificationChannelManagers.createChannels()
    }
}