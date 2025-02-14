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
    val onMemoryUsage: () -> Unit = {},
    val onNetworkStatus: () -> Unit = {},
    val onAlarmManager: () -> Unit = {},
    val onPowerManager: () -> Unit = {}
)

@Composable
fun MainScreen(clickActions: Actions) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = clickActions.onMemoryUsage) {
            Text(text = "Memory Usage")
        }

        Button(onClick = clickActions.onNetworkStatus) {
            Text(text = "ConnectivityManager")
        }

        Button(onClick = clickActions.onPowerManager) {
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

        Button(onClick = {}) {
            /**
             * https://yggr.medium.com/exploring-androids-batterymanager-api-8f64951fd9f6
             */
            Text(text = "Battery manager")
        }
    }
}