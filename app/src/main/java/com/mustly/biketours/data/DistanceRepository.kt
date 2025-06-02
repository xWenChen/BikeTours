package com.mustly.biketours.data

import java.util.*

class DistanceRepository {
    private val dataSource = DistanceDataSource()

    // 返回距离的列表，有序。
    suspend fun getDistanceList(): SortedSet<Int> {
        return dataSource.getDistanceList()
    }

    // 保存距离的列表，有序。
    suspend fun saveDistanceList(list: List<Int>): Boolean {
        return dataSource.saveDistanceList(list)
    }

    // 返回总距离
    suspend fun getTotalDistance(): Long {
        return dataSource.getTotalDistance()
    }

    // 保存总距离
    suspend fun saveTotalDistance(totalDistance: Long): Boolean {
        return dataSource.saveTotalDistance(totalDistance)
    }
}