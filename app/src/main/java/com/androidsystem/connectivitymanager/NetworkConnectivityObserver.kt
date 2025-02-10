package com.androidsystem.connectivitymanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.androidsystem.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class NetworkConnectivityObserver(private val context: Context) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.Status> {
        Log.d(
            TAG,
            "observe() - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
        )
        //when calling scope is cancelled or close, to unregister something  inside awaitClose { }  use callbackFlow
        return callbackFlow {
            Log.d(
                TAG,
                "callbackFlow - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
            )
            val callback =
                object : ConnectivityManager.NetworkCallback() {//control + O: to override methods

                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        launch {
                            send(ConnectivityObserver.Status.Available)
                            Log.d(
                                TAG,
                                "onAvailable - launch - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
                            )
                        }
                    }

                    override fun onLosing(network: Network, maxMsToLive: Int) {
                        super.onLosing(network, maxMsToLive)
                        launch {
                            send(ConnectivityObserver.Status.Losing)
                            Log.d(
                                TAG,
                                "onLosing - launch - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
                            )
                        }
                    }

                    override fun onLost(network: Network) {
                        super.onLost(network)
                        launch {
                            send(ConnectivityObserver.Status.Lost)
                            Log.d(
                                TAG,
                                "onLost - launch - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
                            )
                        }
                    }

                    override fun onUnavailable() {
                        super.onUnavailable()
                        launch { send(ConnectivityObserver.Status.Unavailable) }
                        Log.d(
                            TAG,
                            "onUnavailable - launch - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
                        )
                    }
                }

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
                Log.d(
                    TAG,
                    "awaitClose - Thread: ${Thread.currentThread()}, Id: ${Thread.currentThread().id}"
                )
            }
        }.flowOn(Dispatchers.Default)
            .distinctUntilChanged()//To prevent emissions of same consecutive events more than once.
    }
}