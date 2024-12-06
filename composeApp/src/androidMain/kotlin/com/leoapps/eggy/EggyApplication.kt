package com.leoapps.eggy

import android.app.Application
import com.leoapps.eggy.base.notification.NotificationChannelsManager
import com.leoapps.eggy.di.initKoin
import com.leoapps.eggy.di.platformModule
import com.leoapps.eggy.logs.data.LogDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class EggyApplication : Application() {

    private val notificationChannelManagers: NotificationChannelsManager by inject()
    private val logDao: LogDao by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EggyApplication)
            modules(platformModule)
        }

        notificationChannelManagers.createChannels()

        //todo: avoid using GlobalScope (?) avoid injecting Dao
        GlobalScope.launch {
            logDao.clear()
        }
    }
}