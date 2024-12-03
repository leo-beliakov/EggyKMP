package com.leoapps.eggy

import android.app.Application
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import com.leoapps.eggy.base.notification.NotificationChannelsManager
import com.leoapps.eggy.di.initKoin
import com.leoapps.eggy.di.platformModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class EggyApplication : Application() {

    private val notificationChannelManagers: NotificationChannelsManager by inject()

    override fun onCreate() {

//        lateinit var activityResultRegistryOwner : ActivityResultRegistryOwner
//        activityResultRegistryOwner.activityResultRegistry.register(
//            "",
//            ActivityResultContracts.RequestMultiplePermissions()
//        ) { permissions ->
//             Handle the permissions request result
//
//        }


            super.onCreate()
        initKoin {
            androidContext(this@EggyApplication)
            modules(platformModule)
        }

        notificationChannelManagers.createChannels()
    }
}