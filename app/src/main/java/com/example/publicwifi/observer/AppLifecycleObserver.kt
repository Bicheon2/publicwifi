package com.example.publicwifi.observer

import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class AppLifecycleObserver: LifecycleEventObserver {
    private val lifecycleStateChannel = Channel<Boolean>(Channel.CONFLATED)

    val lifecycleState: Flow<Boolean> = lifecycleStateChannel.receiveAsFlow()
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> {
                // 앱이 백그라운드로 이동함
                lifecycleStateChannel.trySend(false).isSuccess
            }
            Lifecycle.Event.ON_START -> {
                // 앱이 포그라운드로 이동함
                lifecycleStateChannel.trySend(true).isSuccess
            }
            else -> {}
        }
    }


}