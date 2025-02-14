package com.androidsystem.memory

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.androidsystem.R

/**
 * Must check  AndroidManifest file with following
 * <application
 *   android:largeHeap="true"/>
 *
 *  without android:largeHeap="true" - maxMemoryInMB : 192
 *  with android:largeHeap="true" - maxMemoryInMB : 512 : GC will run for longer time -> App lag
 */
class MemoryAllocationTestActivity : ComponentActivity() {
    private val randomList = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        memoryProfileLogger(source = "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_allocation_test)

        memoryProfileLogger(source = "After setContentView")

        Thread() {
            Thread.sleep(5 * 1000)
            /**
             * Observe the Log cat - sometime .5MB (ignored)
             * sometime 1MB memory usage increased due to this thread
             */
            memoryProfileLogger(source = "In side Thread after delay")
        }.start()

        findViewById<Button>(R.id.allocateMoreSpace).setOnClickListener {
            for (id in 1..100000) {
                randomList.add(id)
            }
            memoryProfileLogger("Updated list size: ${randomList.size}")
        }
    }

    companion object {
        fun show(fromActivity: Activity) {
            Intent(fromActivity, MemoryAllocationTestActivity::class.java).also {
                fromActivity.startActivity(it)
            }
        }
    }
}


