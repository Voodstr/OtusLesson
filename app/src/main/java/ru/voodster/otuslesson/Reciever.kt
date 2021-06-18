package ru.voodster.otuslesson

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.voodster.otuslesson.db.FilmEntity


class Receiver :BroadcastReceiver() {
    companion object{
        const val CHANNEL_WATCH = "Watch Later"
        const val CHANNEL_FCM = "FCM"
        const val TAG = "BroadcastReceiver"

        /**
         *  setAlarm()
         *  Notification id
         */
        const val notificationId = 1
    }
    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG,"onReceive")
        val bundle = intent.getBundleExtra("bundle")
        Log.d("bundle: ",bundle.toString())
        val film  = bundle?.getParcelable<FilmEntity>("film")
        Log.d("film: ",film.toString())

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("bundle",bundle)
        }
        val pendingIntent = PendingIntent.getActivity(context,0,activityIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, App.CHANNEL_WATCH)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("Watch film")
            .setContentText(film?.title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }


        }

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout

    }
