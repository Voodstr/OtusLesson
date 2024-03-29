package ru.voodster.otuslesson

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log

import ru.voodster.otuslesson.db.FilmsRoomDatabase


class App:Application() {

    //"http://10.0.2.2/"
    companion object{
        const val BASE_URL = "https://svn.signalmodelling.ru/"

        const val TAG = "App"

        const val CHANNEL_WATCH = "Watch Later"
        const val CHANNEL_FCM = "FCM"
        lateinit var database: FilmsRoomDatabase

        var instance: App? = null
            private set
    }


    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")

        createNotificationChannel()
        FlavorGetter().initFireBase()


    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channelWatch = NotificationChannel(CHANNEL_WATCH, name, importance).apply {
                description = descriptionText
            }
            val channelFCM = NotificationChannel(CHANNEL_FCM, "CloudMessage", importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channelWatch)
            notificationManager.createNotificationChannel(channelFCM)
        }
    }



}

