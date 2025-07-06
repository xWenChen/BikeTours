package com.mustly.biketours.record.bean

import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.record.constants.ERROR_INDEX
import com.mustly.biketours.ui.BikeViewData
import com.mustly.biketours.ui.BikeViewData.Companion.toBikeViewData

// 分组数据，group child 表示第几组第几个。
data class GroupedBikeViewData(
    var groupIndex: Int = ERROR_INDEX,
    var childIndex: Int = ERROR_INDEX,
    var isGroup: Boolean = false,
    var data: BikeViewData? = null,
) {
    companion object {
        fun BikeData?.toGroupedBikeViewData(): GroupedBikeViewData? {
            this ?: return null
            return GroupedBikeViewData(
                data = this.toBikeViewData(),
            )
        }
    }
}