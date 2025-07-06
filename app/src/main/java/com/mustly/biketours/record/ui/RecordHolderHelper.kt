package com.mustly.biketours.record

import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.record.bean.GroupedBikeViewData
import com.mustly.biketours.record.ui.ChildRecordHolder
import com.mustly.biketours.record.ui.GroupRecordHolder

fun RecyclerView.ViewHolder.tryBind(position: Int, data: GroupedBikeViewData) {
    when(this) {
        is GroupRecordHolder -> {
            this.bind(position, data)
        }
        is ChildRecordHolder -> {
            this.bind(position, data)
        }
    }
}