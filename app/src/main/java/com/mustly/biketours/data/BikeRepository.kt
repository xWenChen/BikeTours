package com.mustly.biketours.data

import com.mustly.biketours.database.bean.BikeData
import java.time.Instant
import java.time.ZoneId

/**
 * Repository
 *   DataSource
 *      Dao
 * */
class BikeRepository {
    private val dataSource = BikeDataSource()

    suspend fun insertOrUpdateBikeData(data: BikeData?): BikeData? {
        return dataSource.insertOrUpdateBikeData(data)
    }

    suspend fun getAllOrderByEndTime(): List<BikeData?>? {
        return dataSource.queryAllOrderByEndTime()
    }

    /**
     * 分页加载数据
     * */
    suspend fun getDataByPage(page: Long, pageSize: Long): List<BikeData?>? {
        return dataSource.getDataByPage(page, pageSize)
    }

    /**
     * 获取今天的骑行记录
     * */
    suspend fun getTodayBikeData(): List<BikeData?>? {
        val time = System.currentTimeMillis()
        val timeRange = getTodayTimeRange(time)
        return dataSource.getRangeRecords(timeRange.first, timeRange.last)
    }

    /**
     * 取左闭右开的区间
     * */
    private fun getTodayTimeRange(timeMs: Long): LongRange {
        val instant = Instant.ofEpochMilli(timeMs)
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())

        // 获取当天的 00:00:00
        val startOfDay = zonedDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault())
        // 获取当天的 23:59:59
        //val endOfDay = startOfDay.plusDays(1).minusSeconds(1)
        // 获取下一天的 00:00:00
        val endOfDay = startOfDay.plusDays(1)

        return LongRange(startOfDay.toInstant().toEpochMilli(), endOfDay.toInstant().toEpochMilli())
    }
}