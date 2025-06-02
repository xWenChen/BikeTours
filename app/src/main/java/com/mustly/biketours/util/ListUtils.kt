package com.mustly.biketours.util

import java.util.*

fun String?.toIntSortedList(): SortedSet<Int> {
    if (this.isNullOrBlank()) {
        return sortedSetOf()
    }
    val set = sortedSetOf<Int>()
    this.split(", ").forEach {
        set.add(it.optInt())
    }
    return set
}