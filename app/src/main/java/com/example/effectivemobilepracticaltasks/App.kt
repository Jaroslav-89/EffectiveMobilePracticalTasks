package com.example.effectivemobilepracticaltasks

import android.app.Application
import com.example.effectivemobilepracticaltasks.topic_1_kotlin.AppStartTimeDelegate

class App : Application() {
    val appStartTime by AppStartTimeDelegate()
}