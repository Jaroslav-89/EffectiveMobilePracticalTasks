package com.example.effectivemobilepracticaltasks.topic_3_rxjava

import io.reactivex.rxjava3.core.Single

fun networkCall(): Single<String> {
    return Single.fromCallable {
        Thread.sleep(2000)
        "Контент с сервера"
    }
}