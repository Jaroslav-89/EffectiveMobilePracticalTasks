package com.example.effectivemobilepracticaltasks.topic_1_kotlin

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class AppStartTimeDelegate : ReadOnlyProperty<Any, Long> {
    private val startTime = System.currentTimeMillis()
    private var loggingJob: Job? = null

    init {
        loggingJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                Log.d("AppStartTime", "Приложение запущено в: ${startTime.toTimeString()}")
                delay(3000)
            }
        }
    }

    fun stopLogging() {
        loggingJob?.cancel()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return startTime
    }

    private fun Long.toTimeString(): String {
        return java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date(this))
    }
}