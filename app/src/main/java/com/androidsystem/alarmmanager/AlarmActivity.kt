package com.androidsystem.alarmmanager

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.androidsystem.ui.theme.AndroidSystemLearningTheme
import java.time.LocalDateTime

class AlarmActivity : ComponentActivity() {
    private var alarmItem: AlarmItem? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidSystemLearningTheme {
                Surface {
                    val alarmScheduler = AlarmObserverImpl(this)
                    AlarmScreen(alarmScheduler)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun AlarmScreen(alarmScheduler: AlarmObserverImpl) {
        var seconds by rememberSaveable {
            mutableStateOf("")
        }
        var message by rememberSaveable {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = seconds,
                onValueChange = {
                    seconds = it
                },
                label = {
                    Text(text = "Alarm After seconds")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = message,
                onValueChange = {
                    message = it
                },
                label = {
                    Text(text = "Alarm Message")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(onClick = {
                    alarmItem = AlarmItem(
                        alarmTime = LocalDateTime.now().plusSeconds(seconds.toLong()),
                        message = message
                    )
                    //Calls the specified function block with this value as its argument and returns its result.
                    alarmItem?.let(alarmScheduler::schedule)
                }) {
                    Text(text = "Schedule Alarm")
                }

                Button(onClick = {
                    alarmItem?.let(alarmScheduler::cancel)
                    alarmItem?.let {
                        alarmScheduler.cancel(it)
                    }

                }) {
                    Text(text = "Cancel Alarm")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, AlarmActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}