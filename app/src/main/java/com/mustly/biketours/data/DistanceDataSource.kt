package com.mustly.biketours.data

import androidx.annotation.WorkerThread
import com.mustly.biketours.util.SPUtils
import com.mustly.biketours.util.SPUtils.getLong
import com.mustly.biketours.util.SPUtils.getString
import com.mustly.biketours.util.SPUtils.put
import com.mustly.biketours.util.toIntSortedList
import java.util.*

class DistanceDataSource {
    val sp = SPUtils.sp()
    // 返回距离的列表，有序。
    @WorkerThread
    suspend fun getDistanceList(): SortedSet<Int> {
        val listStr = sp.getString(KEY_DISTANCE_LIST)
        var set = if (listStr.isNullOrBlank()) {
            defaultSet
        } else {
            listStr.toIntSortedList()
        }
        return set
    }

    // 保存距离的列表，有序。
    @WorkerThread
    suspend fun saveDistanceList(list: List<Int>): Boolean {
        val listStr = list.joinToString()
        if (listStr.isBlank()) {
            return false
        }
        return sp.put(KEY_DISTANCE_LIST, listStr)
    }

    // 返回总距离
    suspend fun getTotalDistance(): Long {
        return sp.getLong(KEY_TOTAL_DISTANCE)
    }

    // 保存总距离
    suspend fun saveTotalDistance(totalDistance: Long): Boolean {
        return sp.put(KEY_TOTAL_DISTANCE, totalDistance)
    }

    companion object {
        val defaultSet = sortedSetOf(4800, 9600)
        const val KEY_DISTANCE_LIST = "distanceList"
        const val KEY_TOTAL_DISTANCE = "totalDistance"
    }
}