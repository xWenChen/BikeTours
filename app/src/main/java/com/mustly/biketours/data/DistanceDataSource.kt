package com.mustly.biketours.data

import androidx.annotation.WorkerThread
import com.mustly.biketours.util.SPUtils
import com.mustly.biketours.util.SPUtils.getString
import com.mustly.biketours.util.SPUtils.put
import com.mustly.biketours.util.toLongSortedList
import java.util.*

class DistanceDataSource {
    val sp = SPUtils.sp()
    // 返回距离的列表，有序。
    @WorkerThread
    suspend fun getDistanceList(): SortedSet<Long> {
        val listStr = sp.getString(KEY_DISTANCE_LIST)
        var set = if (listStr.isNullOrBlank()) {
            defaultSet
        } else {
            listStr.toLongSortedList()
        }
        return set
    }

    // 保存距离的列表，有序。
    @WorkerThread
    suspend fun saveDistanceList(list: List<Long>): Boolean {
        val listStr = list.joinToString()
        if (listStr.isBlank()) {
            return false
        }
        return sp.put(KEY_DISTANCE_LIST, listStr)
    }

    // 返回总距离
    suspend fun getTotalDistance(defaultValue: Long): Long {
        return sp.getLong(KEY_TOTAL_DISTANCE, defaultValue)
    }

    // 保存总距离
    suspend fun saveTotalDistance(totalDistance: Long): Boolean {
        return sp.put(KEY_TOTAL_DISTANCE, totalDistance)
    }

    // 返回上次距离
    suspend fun getLastDistance(defaultValue: Long): Long {
        return sp.getLong(KEY_LAST_DISTANCE, defaultValue)
    }

    // 保存上次距离
    suspend fun saveLastDistance(totalDistance: Long): Boolean {
        return sp.put(KEY_LAST_DISTANCE, totalDistance)
    }

    companion object {
        val defaultSet = sortedSetOf(100L, 500L, 1000L, 4600L, 4800L, 5000L, 9600L, 10_000L)
        const val KEY_DISTANCE_LIST = "distanceList"
        const val KEY_TOTAL_DISTANCE = "totalDistance"
        const val KEY_LAST_DISTANCE = "lastDistance"
    }
}