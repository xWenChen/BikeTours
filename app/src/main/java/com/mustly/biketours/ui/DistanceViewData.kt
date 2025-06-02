package com.mustly.biketours.ui

data class DistanceViewData(
    /**
     * 视图类型
     * */
    val viewTYpe: ViewType,
    /**
     * 当次骑行的距离，单位是米。
     * */
    val distance: Long = 0L,
    /**
     * 是否选中的标识
     * */
    var isChecked: Boolean = false,
)

