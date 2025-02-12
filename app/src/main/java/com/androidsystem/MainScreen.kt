package com.androidsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class Actions(
    val onNetworkStatus: () -> Unit = {},
    val onAlarmManager: () -> Unit = {}
)

@Composable
fun MainScreen(clickActions: Actions) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = clickActions.onNetworkStatus) {
            Text(text = "ConnectivityManager")
        }

        Button(onClick = {}) {
            Text(text = "PowerManager")
        }


        Button(onClick = clickActions.onAlarmManager) {
            Text(text = "AlarmManager")
        }

        Button(onClick = {}) {
            Text(text = "NotificationManager")
        }
        Button(onClick = {}) {
            Text(text = "KeyGuardManager")
        }

        Button(onClick = {}) {
            Text(text = "LocationManager")
        }

        Button(onClick = {}) {
            Text(text = "WifiManager")
        }
    }
}