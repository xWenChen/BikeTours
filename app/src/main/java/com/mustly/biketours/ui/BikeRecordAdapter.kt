package com.mustly.biketours.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.databinding.RecordItemBinding
import com.mustly.biketours.util.TimeUtils
import com.mustly.biketours.util.formatString

class BikeRecordAdapter() : ListAdapter<BikeViewData, BikeRecordHolder>(
    object : DiffUtil.ItemCallback<BikeViewData>() {
        override fun areItemsTheSame(oldItem: BikeViewData, newItem: BikeViewData): Boolean {
            return oldItem.contentId == newItem.contentId
        }

        override fun areContentsTheSame(oldItem: BikeViewData,newItem: BikeViewData): Boolean {
            return oldItem == newItem
        }
    }
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeRecordHolder {
        return BikeRecordHolder(RecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BikeRecordHolder, position: Int) {
        val data = getItem(position)
        holder.bind(position, data)
    }
}

class BikeRecordHolder(val binding: RecordItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, data: BikeViewData) {
        binding.apply {
            val startTimeStr = TimeUtils.parseTimeText(data.startTime)
            val endTimeStr = TimeUtils.parseTimeText(data.endTime)

            // 开始时间
            val startTimeOk = data.startTime > 0L
            tvStartTime.isVisible = startTimeOk
            if (startTimeOk && startTimeStr.isNotEmpty()) {
                tvStartTime.text = startTimeStr
            }

            // 结束时间
            if (endTimeStr.isNotEmpty()) {
                tvEndTime.text = endTimeStr
            }

            // 里程
            val distance = (data.distance.toFloat() / 1000).formatString()
            if (distance.isNotEmpty()) {
                tvDistance.text = "$distance 公里"
            }
        }
    }
}