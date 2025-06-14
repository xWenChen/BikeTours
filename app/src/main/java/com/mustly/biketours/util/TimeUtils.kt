package com.mustly.biketours.util

import android.util.Log
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeUtils {
    const val TAG = "TimeUtils"

    /**
     * 解析毫秒为日期，格式：2025-06-03
     * */
    fun parseDateText(timeMs: Long): String {
        return parseDateTimeInner(timeMs, "yyyy-MM-dd")
    }

    /**
     * 解析毫秒为时间，格式：16:06:03
     * */
    fun parseTimeText(timeMs: Long): String {
        return parseDateTimeInner(timeMs, "HH:mm:ss")
    }

    private fun parseDateTimeInner(timeMs: Long, pattern: String): String {
        if (timeMs <= 0L) {
            return ""
        }

        return try {
            val instant = Instant.ofEpochMilli(timeMs)
            val dateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
            // 格式: 2025-06-03
            val formatter = DateTimeFormatter.ofPattern(pattern)
            dateTime.format(formatter)
        } catch (e: Exception) {
            Log.e(TAG, "parseDateTimeInner", e)
            ""
        }
    }
}