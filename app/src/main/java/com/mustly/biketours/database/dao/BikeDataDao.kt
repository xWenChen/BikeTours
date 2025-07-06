package com.mustly.biketours.database.dao

import androidx.room.*
import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.database.constants.tableName

/**
 * 注意：Room 不支持挂起函数。
 * */
@Dao
interface BikeDataDao {
    @Query("SELECT * FROM $tableName")
    fun queryAll(): List<BikeData>?

    /**
     * ORDER BY createTime ASC // 按时间升序排序（从旧到新）
     * ORDER BY createTime DESC // 按时间降序排序（从新到旧）
     * */
    @Query("SELECT * FROM $tableName ORDER BY endTime DESC")
    fun queryAllOrderByEndTime(): List<BikeData>?

    @Query("SELECT * FROM $tableName WHERE id IN (:ids)")
    fun loadAllByIds(ids: IntArray): List<BikeData>?

    /**
     * LIMIT 1 限制查询单条
     * */
    @Query("SELECT * FROM $tableName WHERE startTime = :startTime AND endTime = :endTime AND distance = :distance LIMIT 1")
    fun query(startTime: Long, endTime: Long, distance: Long): BikeData?

    /**
     * 分页加载
     *
     * ORDER BY createTime ASC // 按时间升序排序（从旧到新）
     * ORDER BY createTime DESC // 按时间降序排序（从新到旧）
     *
     * LIMIT 1 限制查询单条
     * OFFSET 偏移值
     * */
    @Query("SELECT * FROM $tableName ORDER BY endTime DESC LIMIT :size OFFSET :offset")
    fun queryByPage(size: Long, offset: Long): List<BikeData>?

    /**
     * 查询指定结束时间内的数据
     * */
    @Query("SELECT * FROM $tableName WHERE endTime >= :startTimeMsInclude AND endTime < :endTimeMsExclude")
    fun queryEndTimeAt(startTimeMsInclude: Long, endTimeMsExclude: Long): List<BikeData>?

    /**
     * LIMIT 1 限制查询单条
     * */
    @Query("SELECT * FROM $tableName WHERE contentId = :contentId LIMIT 1")
    fun queryById(contentId: Long): BikeData?

    /**
     * @return 插入数据的 rowId
     * */
    /*@Insert
    fun insert(users: BikeData?): Int*/

    @Insert
    fun insertAll(vararg users: BikeData)

    /**
     * @return 成功更新的行数。
     * */
    @Update
    fun updateAll(vararg users: BikeData): Int

    /**
     * @return 成功删除的行数。
     * */
    @Delete
    fun delete(vararg user: BikeData): Int
}