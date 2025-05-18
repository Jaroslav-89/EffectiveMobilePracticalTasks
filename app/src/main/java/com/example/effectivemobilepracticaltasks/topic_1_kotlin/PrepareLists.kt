package com.example.effectivemobilepracticaltasks.topic_1_kotlin

fun prepareMixedList(): List<Any?> {
    return listOf(
        "String value",
        3.14,
        true,
        'A',
        listOf(1, 2, 3),
        mapOf("key" to "value"),
        null,
        Unit,
        object {
            override fun toString() = "Custom object"
        },
        42,
        100500,
    )
}

