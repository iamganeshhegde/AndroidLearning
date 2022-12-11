package com.example.firstcomposeactivity.pokemon

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.firstcomposeactivity.agora.GlobalSettings
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PokeDexApplication : Application() {

    private var globalSettings: GlobalSettings? = null

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channnel = NotificationChannel(
                "DownloadChannel",
                "File Download",
                NotificationManager.IMPORTANCE_HIGH
            )

//            val notificationMatcher = getSystemService(Context.NOTIFICATION_SERVICE)
            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channnel)
        }*/

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "download_channel",
                "File download",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }


    }


}