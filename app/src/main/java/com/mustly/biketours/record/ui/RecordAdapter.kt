package com.mustly.biketours.record.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.databinding.ChildRecordItemBinding
import com.mustly.biketours.databinding.RecordItemTitleBinding
import com.mustly.biketours.record.bean.GroupedBikeViewData
import com.mustly.biketours.record.constants.ViewType
import com.mustly.biketours.record.tryBind
import com.mustly.biketours.util.TimeUtils
import com.mustly.biketours.util.formatString

class RecordAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var list = listOf<GroupedBikeViewData>()

    fun submit(newList: List<GroupedBikeViewData>) {
        list = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType ==ViewType.Group.ordinal) {
            GroupRecordHolder(RecordItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            ChildRecordHolder(ChildRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        holder.tryBind(position, data)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(position: Int): GroupedBikeViewData {
        return list[position]
    }

    override fun getItemViewType(position: Int): Int {
        val data = getItem(position)
        return if (data.isGroup) {
            ViewType.Group.ordinal
        } else {
            ViewType.Child.ordinal
        }
    }
}

class GroupRecordHolder(val binding: RecordItemTitleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, data: GroupedBikeViewData) {
        binding.tvDate.text = TimeUtils.dateTextWithWeekDay(data.data?.endTime)
    }
}

class ChildRecordHolder(val binding: ChildRecordItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, data: GroupedBikeViewData) {
        val realData = data.data ?: return
        binding.apply {
            val startTimeStr = TimeUtils.parseTimeText(realData.startTime)
            val endTimeStr = TimeUtils.parseTimeText(realData.endTime)

            // 开始时间
            val startTimeOk = realData.startTime > 0L
            tvStartTime.isVisible = startTimeOk
            if (startTimeOk && startTimeStr.isNotEmpty()) {
                tvStartTime.text = startTimeStr
            }

            // 结束时间
            if (endTimeStr.isNotEmpty()) {
                tvEndTime.text = endTimeStr
            }

            // 里程
            val distance = (realData.distance.toFloat() / 1000).formatString()
            if (distance.isNotEmpty()) {
                tvDistance.text = "$distance 公里"
            }
        }
    }
}