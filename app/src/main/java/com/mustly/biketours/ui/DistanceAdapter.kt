package com.mustly.biketours.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mustly.biketours.R
import com.mustly.biketours.databinding.DistanceItemBinding
import com.mustly.biketours.util.stringRes

class DistanceAdapter : ListAdapter<DistanceViewData, DistanceHolder>(
    object : DiffUtil.ItemCallback<DistanceViewData>() {
        override fun areItemsTheSame(oldItem: DistanceViewData, newItem: DistanceViewData): Boolean {
            return oldItem.distance == newItem.distance
        }

        override fun areContentsTheSame(oldItem: DistanceViewData,newItem: DistanceViewData): Boolean {
            return oldItem == newItem
        }
    }
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistanceHolder {
        return DistanceHolder(DistanceItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DistanceHolder, position: Int) {
        val data = getItem(position)
        holder.bind(position, data)
    }
}

class DistanceHolder(val binding: DistanceItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int, data: DistanceViewData) {
        binding.apply {
            tvDistance.isVisible = true
            tvDistance.text = if (data.viewTYpe == ViewType.ADD_ICON) {
                R.string.custom.stringRes
            } else {
                data.distance.toString()
            }
        }
    }
}