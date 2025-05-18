package com.example.effectivemobilepracticaltasks.topic_1_kotlin

fun shakerSortWithNulls(input: List<Int?>?): List<Int?> {
    if (input == null) return emptyList()

    val notNullList = input.filterNotNull().toMutableList()
    val nullCount = input.size - notNullList.size

    var left = 0
    var right = notNullList.size - 1
    var swapped: Boolean

    do {
        swapped = false

        for (i in left until right) {
            if (notNullList[i] > notNullList[i + 1]) {
                notNullList[i] = notNullList[i + 1].also { notNullList[i + 1] = notNullList[i] }
                swapped = true
            }
        }
        right--

        for (i in right downTo left + 1) {
            if (notNullList[i] < notNullList[i - 1]) {
                notNullList[i] = notNullList[i - 1].also { notNullList[i - 1] = notNullList[i] }
                swapped = true
            }
        }
        left++
    } while (swapped)

    return notNullList + List(nullCount) { null }
}