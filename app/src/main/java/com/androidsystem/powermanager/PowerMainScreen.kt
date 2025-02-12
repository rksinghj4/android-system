package com.androidsystem.powermanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class PowerManagerActions(
    val onWakeupLocks: () -> Unit = {},
)

@Composable
fun PowerMainScreen(powerManagerActions: PowerManagerActions) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = powerManagerActions.onWakeupLocks) {
            Text(text = "Wakeup Locks")
        }

        Button(onClick = powerManagerActions.onWakeupLocks) {
            Text(text = "")
        }

        Button(onClick = powerManagerActions.onWakeupLocks) {
            Text(text = "")
        }

        Button(onClick = {}) {
            Text(text = "")
        }
        Button(onClick = {}) {
            Text(text = "")
        }

        Button(onClick = {}) {
            Text(text = "")
        }

        Button(onClick = {}) {
            Text(text = "")
        }
    }
}