package com.example.effectivemobilepracticaltasks.topoc_2_coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun <T> Flow<T>.throttleLatest(periodMillis: Long): Flow<T> = channelFlow {
    var lastValue: T?
    var lastEmissionTime = 0L

    collect { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmissionTime > periodMillis) {
            lastValue = null
            send(value)
            lastEmissionTime = currentTime
            launch {
                delay(periodMillis)
                lastValue?.let {
                    send(it)
                    lastValue = null
                    lastEmissionTime = 0L
                }
            }
        } else {
            lastValue = value
        }
    }
}
