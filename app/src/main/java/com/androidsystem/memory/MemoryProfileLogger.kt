package com.androidsystem.memory

import android.util.Log

const val TAG = "MEMORY_PROFILE"
fun memoryProfileLogger(source: String) {
    //maxMemory() - returns bytes
    val maxMemoryInMB = (Runtime.getRuntime().maxMemory() / 1024) / 1024
    val totalMemoryInMB = Runtime.getRuntime().totalMemory() / 1024 / 1024
    val freeMemoryInMB = Runtime.getRuntime().freeMemory() / 1024 / 1024
    val usedMemoryInMB = totalMemoryInMB - freeMemoryInMB

    Log.d(TAG, "***************************")
    Log.d(TAG, "Start for the source: $source")

    Log.d(TAG, "maxMemoryInMB : $maxMemoryInMB")
    Log.d(TAG, "totalMemoryInMB : $totalMemoryInMB")
    Log.d(TAG, "freeMemoryInMB : $freeMemoryInMB")
    Log.d(TAG, "usedMemoryInMB : $usedMemoryInMB")

    Log.d(TAG, "End for the source: $source")
    Log.d(TAG, "***************************")
}