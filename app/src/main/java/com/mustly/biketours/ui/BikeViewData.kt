package com.mustly.biketours.ui

import com.mustly.biketours.database.bean.BikeData

/**
 * 骑行数据
 * */
data class BikeViewData(
    var id: Long = 0,
    /**
     * 内容id
     * */
    var contentId: Long = 0L,
    /**
     * 骑行起始时间戳，单位是毫秒。
     * */
    var startTime: Long = 0,
    /**
     * 骑行结束时间戳，单位是毫秒。
     * */
    var endTime: Long = 0,
    /**
     * 当次骑行的距离，单位是米。
     * */
    var distance: Long = 0,
) {
    companion object {
        fun BikeData?.toBikeViewData(): BikeViewData? {
            this ?: return null
            return BikeViewData(
                this.id,
                this.contentId,
                this.startTime,
                this.endTime,
                this.distance,
            )
        }
    }
}