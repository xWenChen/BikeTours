package com.mustly.biketours.util

import java.util.*

fun String?.toLongSortedList(): SortedSet<Long> {
    if (this.isNullOrBlank()) {
        return sortedSetOf()
    }
    val set = sortedSetOf<Long>()
    this.split(", ").forEach {
        set.add(it.optLong())
    }
    return set
}