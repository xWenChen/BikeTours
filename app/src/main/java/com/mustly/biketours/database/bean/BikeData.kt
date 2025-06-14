package com.mustly.biketours.database.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mustly.biketours.database.constants.tableName

/**
 * 骑行数据。
 *
 * Room 不允许默认值。
 * */
@Entity(tableName = tableName)
class BikeData(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    /**
     * 内容id
     * */
    @ColumnInfo
    var contentId: Long,
    /**
     * 骑行起始时间戳，单位是毫秒。
     * */
    @ColumnInfo
    var startTime: Long,
    /**
     * 骑行结束时间戳，单位是毫秒。
     * */
    @ColumnInfo
    var endTime: Long,
    /**
     * 当次骑行的距离，单位是米。
     * */
    @ColumnInfo
    var distance: Long,
) {
    /**
     * Room 不允许多构造函数，忽略此选项。
     * */
    @Ignore
    constructor(contentId: Long, endTime: Long, distance: Long) : this(0L, contentId, 0L, endTime, distance)
}