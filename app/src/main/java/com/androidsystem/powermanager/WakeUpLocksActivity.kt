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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.androidsystem.ui.theme.AndroidSystemLearningTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * https://yggr.medium.com/exploring-android-powermanager-api-72981adbafb1
 */
class WakeUpLocksActivity : ComponentActivity() {

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
                        OutlinedTextField(
                            value = "Test wake lock effect on on keyboard",
                            onValueChange = {})

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { fullWakeLock() }) {
                            Text(text = "Full wake lock test")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { dimWakeLock() }) {
                            Text(text = "Dim wake lock test")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

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

    private fun dimWakeLock() {
        if (powerManager.isWakeLockLevelSupported(PowerManager.SCREEN_DIM_WAKE_LOCK)) {
            lifecycleScope.launch(Dispatchers.IO) {
                val wakelock = powerManager.newWakeLock(
                    PowerManager.SCREEN_DIM_WAKE_LOCK,
                    "MYPower: DimWakeLockTAG"
                )
                wakelock.acquire(1 * 60 * 1000)// 1 minutes
                /**
                 * Your device will be on for 30 second, even if it was suppose to sleep after 15 seconds
                 * because of Screen time out was set to 15 seconds in phone's settings.
                 *
                 * But after setting time is elapsed it screen light will be dim.
                 */
                Thread.sleep(30 * 1000)//mimic the long running tak which needs the wake lock
                wakelock.release()//Finally release the wake lock after your task is done.
            }
        } else {
            Toast.makeText(this, "SCREEN_DIM_WAKE_LOCK not  support", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun fullWakeLock() {
        //as long as this window is visible to the user, keep the device's screen turned on and bright.
        if (powerManager.isWakeLockLevelSupported(PowerManager.FULL_WAKE_LOCK)) {
            lifecycleScope.launch(Dispatchers.IO) {
                val wakelock = powerManager.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK,
                    "MYPower: FullWakeLockTAG"
                )

                wakelock.acquire(1 * 60 * 1000)// 1 minutes
                /**
                 * Your device light will be on for 40 second, even if it was suppose to sleep after 15 seconds
                 * because of Screen time out was set to 15 seconds in phone's settings.
                 *
                 * even after setting time out is elapsed screen light will full bright as it was initially.
                 */
                Thread.sleep(40 * 1000)//mimic the long running tak which needs the wake lock
                wakelock.release()//Finally release the wake lock after your task is done.
            }
        } else {
            Toast.makeText(this, "FLAG_KEEP_SCREEN_ON not  support", Toast.LENGTH_LONG)
                .show()
        }
    }


    companion object {
        private const val TAG = "PROXIMITY"
        fun show(fromActivity: Activity) {
            Intent(fromActivity, WakeUpLocksActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}