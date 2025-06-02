package com.mustly.biketours.util

fun String?.optInt(): Int {
    if (this.isNullOrEmpty()) {
        return 0
    }
    return try {
        this.toInt()
    } catch (e: Exception) {
        0
    }
}