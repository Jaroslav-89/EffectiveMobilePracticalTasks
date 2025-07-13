package com.example.effectivemobilepracticaltasks.topic_3_rxjava

import io.reactivex.rxjava3.subjects.PublishSubject

object Subject {
    val itemClickSubject = PublishSubject.create<Int>()
}