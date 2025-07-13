package com.example.effectivemobilepracticaltasks.topic_3_rxjava

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

fun getServer1Cards(): Observable<List<String>> {
    return Observable.just(listOf("Card1A", "Card1B"))
        .delay(1, TimeUnit.SECONDS)
}

fun getServer2Cards(shouldFail: Boolean): Observable<List<String>> {
    return if (shouldFail) {
        Observable.error<List<String>>(RuntimeException("Server 2 failed"))
    } else {
        Observable.just(listOf("Card2A", "Card2B"))
            .delay(2, TimeUnit.SECONDS)
    }
}