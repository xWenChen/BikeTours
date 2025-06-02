package com.mustly.biketours.database.dao

import androidx.room.*
import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.database.constants.tableName

@Dao
interface BikeDataDao {
    @Query("SELECT * FROM $tableName")
    fun getAll(): List<BikeData>

    @Query("SELECT * FROM $tableName WHERE id IN (:userIds)")
    fun loadAllByIds(ids: IntArray): List<BikeData>?

    /**
     * LIMIT 1 限制查询单条
     * */
    @Query("SELECT * FROM $tableName WHERE startTime = :startTime AND endTime = :endTime AND distance = :distance LIMIT 1")
    fun query(startTime: Long, endTime: Long, distance: Long): BikeData?

    /**
     * LIMIT 1 限制查询单条
     * */
    @Query("SELECT * FROM $tableName WHERE startTime = :data.startTime AND endTime = :data.endTime AND distance = :data.distance LIMIT 1")
    fun query(data: BikeData): BikeData?

    @Insert
    fun insertAll(vararg users: BikeData): Int

    @Update
    fun updateAll(vararg users: BikeData): Int

    @Delete
    fun delete(user: BikeData)
}