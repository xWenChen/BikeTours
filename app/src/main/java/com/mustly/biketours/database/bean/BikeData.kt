package com.mustly.biketours.database.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mustly.biketours.database.constants.tableName

/**
 * 骑行数据
 * */
@Entity(tableName = tableName)
data class BikeData(
    @PrimaryKey(autoGenerate = true) val id: Long,
    /**
     * 时间戳，单位是毫秒。
     * */
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    /**
     * 当次骑行的距离，单位是米。
     * */
    @ColumnInfo(name = "distance") val distance: Long,
)