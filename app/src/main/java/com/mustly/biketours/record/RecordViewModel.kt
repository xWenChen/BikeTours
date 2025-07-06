package com.mustly.biketours.record

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.mustly.biketours.data.BikeRepository
import com.mustly.biketours.data.DistanceRepository
import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.record.bean.GroupedBikeViewData
import com.mustly.biketours.record.bean.GroupedBikeViewData.Companion.toGroupedBikeViewData
import com.mustly.biketours.record.constants.ERROR_INDEX
import com.mustly.biketours.util.notSameDayTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordViewModel(application: Application) : AndroidViewModel(application) {
    private val distanceRepo = DistanceRepository()
    private val bikeRepository = BikeRepository()
    private val pageSize = PAGE_SIZE

    val recordList = MutableLiveData<Result>()

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            // loadRecordsList(0L)
            loadAll()
        }
    }

    private suspend fun loadAll() {
        val set = bikeRepository.getAllOrderByEndTime()
        if (set.isNullOrEmpty()) {
            return
        }
        dealDbData(set)
    }

    private suspend fun loadRecordsList(page: Long) {
        val set = bikeRepository.getDataByPage(page, pageSize)
        if (set.isNullOrEmpty()) {
            return
        }
        dealDbData(set)
    }

    private fun dealDbData(set: List<BikeData?>) {
        val list = mutableListOf<GroupedBikeViewData>()
        var groupIndex = ERROR_INDEX
        var childIndex = ERROR_INDEX
        // 生成分组数据
        set.forEach {
            val viewData = it.toGroupedBikeViewData() ?: return@forEach
            // 将数据按天分组
            if (isNewData(list, viewData)) {
                groupIndex += 1
                // 重置子数据
                childIndex = 0
                list.add(
                    GroupedBikeViewData(
                        groupIndex = groupIndex,
                        childIndex = ERROR_INDEX,
                        isGroup = true,
                        data = viewData.data,
                    )
                )
            }
            viewData.groupIndex = groupIndex
            viewData.childIndex = childIndex
            viewData.isGroup = false
            childIndex += 1
            list.add(viewData)
        }

        // 生成差量数据
        val oldList = recordList.value?.dataList
        if (oldList.isNullOrEmpty()) {
            recordList.postValue(Result(false, null, list))
            return
        }

        val newList = mutableListOf<GroupedBikeViewData>().apply {
            addAll(oldList)
            addAll(list)
        }

        val diffResult = calculateDiff(oldList, newList)

        recordList.postValue(Result(true, diffResult, newList))
    }

    private fun calculateDiff(oldList: List<GroupedBikeViewData>, newList: MutableList<GroupedBikeViewData>): DiffUtil.DiffResult {
        val cb = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldData = oldList[oldItemPosition]
                val newData = newList[newItemPosition]

                return oldData.isGroup == newData.isGroup && oldData.data?.contentId == newData.data?.contentId
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldData = oldList[oldItemPosition]
                val newData = newList[newItemPosition]

                return oldData == newData
            }
        }

        return DiffUtil.calculateDiff(cb)
    }

    private fun isNewData(list: MutableList<GroupedBikeViewData>, viewData: GroupedBikeViewData): Boolean {
        return list.getOrNull(list.size - 1)?.data?.endTime.notSameDayTo(viewData.data?.endTime)
    }

    companion object {
        const val PAGE_SIZE = 20L
    }
}

data class Result(
    var needDiff: Boolean = false,
    var diffResult: DiffUtil.DiffResult? = null,
    var dataList: List<GroupedBikeViewData>? = null
) {
    fun clear() {
        needDiff = false
        diffResult = null
        dataList = null
    }
}