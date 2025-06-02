package com.mustly.biketours.data

import java.util.*

class DistanceRepository {
    private val dataSource = DistanceDataSource()

    // 返回距离的列表，有序。
    suspend fun getDistanceList(): SortedSet<Long> {
        return dataSource.getDistanceList()
    }

    // 保存距离的列表，有序。
    suspend fun saveDistanceList(list: List<Long>): Boolean {
        return dataSource.saveDistanceList(list)
    }

    // 返回总距离
    suspend fun getTotalDistance(defaultValue: Long = 0): Long {
        return dataSource.getTotalDistance(defaultValue)
    }

    // 保存总距离
    suspend fun saveTotalDistance(totalDistance: Long): Boolean {
        return dataSource.saveTotalDistance(totalDistance)
    }

    // 返回上次的距离
    suspend fun getLastDistance(defaultValue: Long = 0): Long {
        return dataSource.getLastDistance(defaultValue)
    }

    // 保存上次的距离
    suspend fun saveLastDistance(totalDistance: Long): Boolean {
        return dataSource.saveLastDistance(totalDistance)
    }
}