package com.androidsystem.powermanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.androidsystem.ui.theme.AndroidSystemLearningTheme

class PowerManagerOptionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidSystemLearningTheme {
                Surface {
                    PowerMainScreen(clickActions(this))
                }
            }
        }
    }

    private fun clickActions(activity: Activity) = PowerManagerActions(
        onProximitySensor = {
            ProximityManagerActivity.show(fromActivity = activity)
        }
    )

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, PowerManagerOptionsActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}