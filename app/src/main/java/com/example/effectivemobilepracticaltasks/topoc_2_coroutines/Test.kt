package com.example.effectivemobilepracticaltasks.topoc_2_coroutines

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


fun throttleLatestTest() {
    GlobalScope.launch(Dispatchers.IO) {
        flow {
            emit("1")
            delay(100)
            emit("2")
            delay(100)
            emit("2,1")
            //следующее значение по идее должно проходить как последнее, тк укладывается в 300мс
            //но оно проходит только если вместо delay 100 поставить 90-95мс,
            // как это победить с ходу не придумал, но возможно так и дожно работать?
            delay(100)
            emit("2,2")
            delay(100)
            emit("3")
            delay(300)
            emit("4")
            delay(100)
            emit("5")
            delay(100)
            emit("5,1")
        }
            .throttleLatest(300)
            .collect { Log.d("throttleLatestTest", "throttleLatest: $it") }
    }
}

fun throttleFirstTest() {
    GlobalScope.launch(Dispatchers.IO) {
        flow {
            emit("1")
            delay(100)
            emit("2")
            delay(100)
            emit("2,1")
            delay(100)
            emit("2,2")
            delay(100)
            emit("3")
            delay(300)
            emit("4")
            delay(100)
            emit("5")
            delay(100)
            emit("5,1")
        }
            .throttleFirst(300)
            .collect { Log.d("throttleFirstTest", "throttleFirst: $it") }
    }
}