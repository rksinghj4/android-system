package com.androidsystem.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.androidsystem.ALARM_TAG
import com.androidsystem.EXTRA_MESSAGE
import java.time.ZoneId


class AlarmObserverImpl(private val context: Context) : AlarmObserver {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    //private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedule(alarmItem: AlarmItem) {
        //AlarmReceiver is a custom class which implements BroadcastReceiver
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(EXTRA_MESSAGE, alarmItem.message)
        }

        val alarmTime = alarmItem.alarmTime.atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000L //time in millis

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,//type: RTC_WAKEUP - turn on the screen light when the alarm is triggered.
            alarmTime,//triggerAtMillis
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),//unique requestCode - used for cancelling the alarm.
                intent,//Intent
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE //Flags
            )// pending operation - this should happen next when the alarm is triggered.
        )

        Log.d(ALARM_TAG, "Alarm set at : $alarmTime")
    }

    override fun cancel(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),//same requestCode to cancel the alarm
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}


