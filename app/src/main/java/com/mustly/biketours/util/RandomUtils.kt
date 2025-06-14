package com.mustly.biketours.util

import java.util.*

/**
 * 生成 long 型的随机内容id
 * */
fun generateContentId(): Long {
    val uuid = UUID.randomUUID()
    return uuid.mostSignificantBits xor uuid.leastSignificantBits
}