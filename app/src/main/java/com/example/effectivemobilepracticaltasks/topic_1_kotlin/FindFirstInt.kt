package com.example.effectivemobilepracticaltasks.topic_1_kotlin

fun List<Any?>.findFirstInt(): Int? {
    return this.firstOrNull { it is Int } as? Int
}