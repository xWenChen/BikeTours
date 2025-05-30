package com.mustly.biketours.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.database.constants.tableName

@Dao
interface BikeDataDao {
    @Query("SELECT * FROM $tableName")
    fun getAll(): List<BikeData>

    @Query("SELECT * FROM $tableName WHERE id IN (:userIds)")
    fun loadAllByIds(ids: IntArray): List<BikeData>

    @Insert
    fun insertAll(vararg users: BikeData)

    @Delete
    fun delete(user: BikeData)
}