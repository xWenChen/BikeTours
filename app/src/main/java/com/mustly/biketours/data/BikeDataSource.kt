package com.mustly.biketours.data

import android.util.Log
import com.mustly.biketours.database.BikeDatabase
import com.mustly.biketours.database.bean.BikeData

class BikeDataSource {
    private val dao = BikeDatabase.db.bikeDataDao()

    /**
     * 更新或者插入一条数据
     *
     * @return 返回数据库的数据，为空表示失败。
     * */
    suspend fun insertOrUpdateBikeData(data: BikeData?): BikeData? {
        data ?: return null
        try {
            val dbData = dao.query(data.startTime, data.endTime, data.distance)
            return if (dbData == null) {
                // 插入
                dao.insertAll(data)
                // 查询插入的数据
                dao.queryById(data.contentId)
            } else {
                // 更新
                data.id = dbData.id
                val count = dao.updateAll(data)
                if (count > 0) {
                    // 查询插入的数据
                    dao.queryById(data.contentId)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "", e)
            return null
        }
    }

    suspend fun queryAllOrderByEndTime(): List<BikeData?>? {
        return try {
            dao.queryAllOrderByEndTime()
        } catch (e: Exception) {
            Log.e(TAG, "getAllRecord", e)
            null
        }
    }

    /**
     * 分页加载数据
     * */
    suspend fun getDataByPage(page: Long, pageSize: Long): List<BikeData?>? {
        return try {
            val offset = page * pageSize
            dao.queryByPage(pageSize, offset)
        } catch (e: Exception) {
            Log.e(TAG, "getDataByPage", e)
            null
        }
    }

    /**
     * 获取指定时间范围的骑行记录
     * */
    suspend fun getRangeRecords(startTimeMsInclude: Long, endTimeMsExclude: Long): List<BikeData>? {
        return try {
            dao.queryEndTimeAt(startTimeMsInclude, endTimeMsExclude)
        } catch (e: Exception) {
            Log.e(TAG, "getRangeRecords", e)
            null
        }
    }

    companion object {
        const val TAG = "BikeDataSource"
    }
}