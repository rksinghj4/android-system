package com.androidsystem.alarmmanager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.androidsystem.ALARM_TAG
import com.androidsystem.CHANNEL_ID
import com.androidsystem.EXTRA_MESSAGE
import com.androidsystem.R

class AlarmReceiver : BroadcastReceiver() {

    //Registered in manifest
    override fun onReceive(context: Context?, intent: Intent?) {
        //1. Grab the message from intent, 2. prepare the Notification with same message and 3. notify
        val message = intent?.getStringExtra(EXTRA_MESSAGE) ?: return //1
        context?.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //Note: Notification will be notified on this channel with - CHANNEL_ID
            val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm demo")
                .setContentText("Notification sent with message: $message")//2
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notificationManager.notify(NOTIFICATION_ID, builder.build())//3
            Log.d(ALARM_TAG, "onReceive - Message $message")
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}



