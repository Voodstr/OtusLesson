package ru.voodster.otuslesson.service

import android.R
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.voodster.otuslesson.App
import ru.voodster.otuslesson.MainActivity

class MyFirebaseMessagingService() : FirebaseMessagingService(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyFirebaseMessagingService> {
        override fun createFromParcel(parcel: Parcel): MyFirebaseMessagingService {
            return MyFirebaseMessagingService(parcel)
        }

        override fun newArray(size: Int): Array<MyFirebaseMessagingService?> {
            return arrayOfNulls(size)
        }
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        Log.d("FirebaseMessaging","onMessageReceived")
        if (msg.data.containsKey("filmid")){
            sendNotification(msg)
        }


    }

    private fun sendNotification(msg:RemoteMessage) {
        Log.d("FirebaseMessaging","sendNotification")
        val filmid = msg.data["filmid"]
        Log.d("FirebaseMessaging","$filmid")
        val bundle = Bundle()
        bundle.putString("filmid",filmid)
        val activityIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("bundle",bundle)
        }
        val pendingIntent =
            PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(this, App.CHANNEL_FCM)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle("Watch film")
            .setContentText("NOW")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(2, builder.build())
        }
    }
}