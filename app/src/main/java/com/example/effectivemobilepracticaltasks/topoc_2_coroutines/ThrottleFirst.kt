package com.example.effectivemobilepracticaltasks.topoc_2_coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    return flow {
        var lastEmissionTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastEmissionTime >= periodMillis) {
                lastEmissionTime = currentTime
                emit(value)
            }
        }
    }
}