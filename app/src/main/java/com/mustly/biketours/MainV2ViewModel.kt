package com.mustly.biketours

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mustly.biketours.data.DistanceRepository
import com.mustly.biketours.ui.DistanceViewData
import com.mustly.biketours.ui.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainV2ViewModel(application: Application) : AndroidViewModel(application) {
    private val distanceRepo = DistanceRepository()

    val distanceList = MutableLiveData<List<DistanceViewData>>()

    val totalDistance = MutableLiveData(INIT_DISTANCE)
    var lastDistance = LAST_DISTANCE

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            loadLastDistance()
            val listAsync = async { loadDistanceList() }
            val totalAsync = async { loadTotalDistance() }
            listAsync.await()
            totalAsync.await()
        }
    }

    private suspend fun loadDistanceList() {
        val set = distanceRepo.getDistanceList()
        val list = mutableListOf<DistanceViewData>()
        set.forEach {
            list.add(
                DistanceViewData(
                    ViewType.NUMBER,
                    it.toLong(),
                    isChecked = lastDistance == it
                )
            )
        }
        // 添加加号
        list.add(DistanceViewData(ViewType.ADD_ICON))

        distanceList.postValue(list)
    }

    private suspend fun loadTotalDistance() {
        val distance = distanceRepo.getTotalDistance(defaultValue = INIT_DISTANCE)
        totalDistance.postValue(distance)
    }

    private suspend fun loadLastDistance() {
        lastDistance = distanceRepo.getLastDistance(defaultValue = LAST_DISTANCE)
    }

    /**
     * 增加一次骑行记录
     * */
    fun addBikeRecord() {
        val nowValue = totalDistance.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val newValue = nowValue + lastDistance
            if (distanceRepo.saveTotalDistance(newValue)){
                totalDistance.postValue(newValue)
            }
        }
    }

    fun changeCheckedItem(position: Int, data: DistanceViewData) {
        val newList = mutableListOf<DistanceViewData>()
        val oldList = distanceList.value
        if (oldList.isNullOrEmpty()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            oldList.forEach { oldData ->
                // Differ 比对需要完全不同的对象。
                oldData.copy().let { newData ->
                    newData.isChecked = if (newData.distance == data.distance) {
                        // 变更后，保存数据
                        lastDistance = newData.distance
                        distanceRepo.saveLastDistance(newData.distance)
                        true
                    } else {
                        false
                    }
                    newList.add(newData)
                }
            }
            distanceList.postValue(newList)
        }
    }

    companion object {
        // 初始的距离，单位：米
        const val INIT_DISTANCE = 250_000L
        // 上次骑行的距离，单位：米
        const val LAST_DISTANCE = 4800L
    }
}