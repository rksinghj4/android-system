package com.androidsystem

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.androidsystem.alarmmanager.AlarmActivity
import com.androidsystem.connectivitymanager.NetworkConnectivityActivity
import com.androidsystem.memory.MemoryAllocationTestActivity
import com.androidsystem.powermanager.PowerManagerOptionsActivity
import com.androidsystem.ui.theme.AndroidSystemLearningTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidSystemLearningTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(clickActions = clickActions(this))
                }
            }
        }
    }
}

private fun clickActions(activity: Activity) = Actions(
    onMemoryUsage = {
        MemoryAllocationTestActivity.show(activity)
    },
    onNetworkStatus = {
        NetworkConnectivityActivity.show(activity = activity)
    },
    onAlarmManager = {
        AlarmActivity.show(activity)
    },
    onPowerManager = {
        PowerManagerOptionsActivity.show(activity)
    }
)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidSystemLearningTheme {
        Greeting("Android")
    }
}