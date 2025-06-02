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

fun String?.optLong(): Long {
    if (this.isNullOrEmpty()) {
        return 0L
    }
    return try {
        this.toLong()
    } catch (e: Exception) {
        0L
    }
}

// 浮点数转成指定小数位数的字符串。
fun Float.formatString(digits: Int) = "%.${digits}f".format(this)