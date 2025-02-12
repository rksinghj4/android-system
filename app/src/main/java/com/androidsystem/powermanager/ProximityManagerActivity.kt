package com.androidsystem.powermanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.androidsystem.ui.theme.AndroidSystemLearningTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProximityManagerActivity : ComponentActivity() {

    private val powerManager: PowerManager by lazy {
        getSystemService(POWER_SERVICE) as PowerManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidSystemLearningTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = { proximity() }) {
                            Text(text = "Proximity test")
                        }
                    }
                }
            }
        }
    }

    private fun proximity() {
        if (powerManager.isWakeLockLevelSupported(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK)) {
            lifecycleScope.launch(Dispatchers.IO) {
                val wakelock = powerManager.newWakeLock(
                    PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
                    "MYPower: ProximityTAG"
                )

                wakelock.acquire(2 * 60 * 1000)// 2 minutes
                Thread.sleep(10 * 1000)//mimic the long running tak which needs the wake lock
                wakelock.release()//Finally release the wake lock after your task is done.
            }
        } else {
            Toast.makeText(this, "PROXIMITY_SCREEN_OFF_WAKE_LOCK not  support", Toast.LENGTH_LONG)
                .show()
        }
    }


    companion object {
        private const val TAG = "PROXIMITY"
        fun show(fromActivity: Activity) {
            Intent(fromActivity, ProximityManagerActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}