package com.mustly.biketours.util

import android.util.Log
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

object TimeUtils {
    private const val TAG = "TimeUtils"

    private const val DATE_TEMPLATE = "yyyy-MM-dd"

    /**
     * 解析毫秒为日期，格式：2025-06-03
     * */
    fun parseDateText(timeMs: Long?): String {
        return parseDateTimeInner(timeMs, DATE_TEMPLATE)
    }

    /**
     * 解析毫秒为日期，格式：2025-06-03
     * */
    fun dateTextWithWeekDay(timeMs: Long?): String {
        if (timeMs == null || timeMs <= 0L) {
            return ""
        }

        return try {
            val instant = Instant.ofEpochMilli(timeMs)
            val zonedDateTime = instant.atZone(ZoneId.systemDefault())
            val dateTime = zonedDateTime.toLocalDateTime()
            val date = zonedDateTime.toLocalDate()
            // 格式: 2025-06-03
            val formatter = DateTimeFormatter.ofPattern(DATE_TEMPLATE)
            val dateStr = dateTime.format(formatter)
            // 格式：周一
            val weekDay = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            "$dateStr $weekDay"
        } catch (e: Exception) {
            Log.e(TAG, "parseDateTimeInner", e)
            ""
        }
    }

    /**
     * 解析毫秒为时间，格式：16:06:03
     * */
    fun parseTimeText(timeMs: Long): String {
        return parseDateTimeInner(timeMs, "HH:mm:ss")
    }

    private fun parseDateTimeInner(timeMs: Long?, pattern: String): String {
        if (timeMs == null || timeMs <= 0L) {
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

/**
 * 判断是否是一天
 * */
fun Long?.isSameDayTo(timestamp: Long?): Boolean {
    this ?: return false
    timestamp ?: return false
    val thisDate = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    val otherDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
    return thisDate == otherDate
}

/**
 * 判断是否是一天
 * */
fun Long?.notSameDayTo(timestamp: Long?): Boolean {
    this ?: return true
    timestamp ?: return true
    val thisDate = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
    val otherDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
    return thisDate != otherDate
}