package com.mustly.biketours

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mustly.biketours.data.BikeRepository
import com.mustly.biketours.data.DistanceRepository
import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.ui.BikeViewData
import com.mustly.biketours.ui.BikeViewData.Companion.toBikeViewData
import com.mustly.biketours.ui.DistanceViewData
import com.mustly.biketours.ui.ViewType
import com.mustly.biketours.util.generateContentId
import com.mustly.biketours.util.optLong
import com.mustly.biketours.view.input.InputDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainV2ViewModel(application: Application) : AndroidViewModel(application) {
    private val distanceRepo = DistanceRepository()
    private val bikeRepository = BikeRepository()

    val distanceList = MutableLiveData<List<DistanceViewData>>()

    val todayRecords = MutableLiveData<List<BikeViewData>>()

    val totalDistance = MutableLiveData(INIT_DISTANCE)
    var lastDistance = LAST_DISTANCE

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            loadLastDistance()
            val listAsync = async { loadDistanceList() }
            val recordAsync = async { loadRecordsList() }
            val totalAsync = async { loadTotalDistance() }
            listAsync.await()
            recordAsync.await()
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
        list.add(DistanceViewData(ViewType.CUSTOM))

        distanceList.postValue(list)
    }

    private suspend fun loadRecordsList() {
        val set = bikeRepository.getTodayBikeData()
        if (set.isNullOrEmpty()) {
            return
        }
        val list = mutableListOf<BikeViewData>()
        set.forEach {
            val viewData = it.toBikeViewData() ?: return@forEach
            list.add(viewData)
        }

        todayRecords.postValue(list)
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
    fun addBikeRecord(context: Context, distance: Long = lastDistance) {
        val nowValue = totalDistance.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            val data = saveToDb(time, distance)
            if (data == null) {
                Toast.makeText(context, "骑行记录添加失败", Toast.LENGTH_LONG)
                return@launch
            }
            val newValue = nowValue + distance
            if (!distanceRepo.saveTotalDistance(newValue)){
                return@launch
            }
            // 通知页面刷新
            totalDistance.postValue(newValue)
            // 刷新记录列表
            val newList = copyBikeRecords()
            newList.add(data)
            todayRecords.postValue(newList)
        }
    }

    private suspend fun saveToDb(time: Long, distance: Long): BikeViewData? {
        // 先以结束时间作为打卡时间。
        val dbData = bikeRepository.insertOrUpdateBikeData(
            BikeData(
                contentId = generateContentId(),
                endTime = time,
                distance = distance,
            )
        )
        return dbData.toBikeViewData()
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

    fun showInputDialog(activity: FragmentActivity) {
        InputDialogFragment().apply {
            this.onConfirm = {
                val num = it.optLong()
                if (num > 0L) {
                    addBikeRecord(activity, num)
                }
            }
        }.show(activity.supportFragmentManager, "InputDialogFragment")
    }

    private fun copyBikeRecords(): MutableList<BikeViewData> {
        val newList = mutableListOf<BikeViewData>()
        val oldList = todayRecords.value
        if (oldList.isNullOrEmpty()) {
            return newList
        }
        oldList.forEach {
            newList.add(it.copy())
        }
        return newList
    }

    companion object {
        // 初始的距离，单位：米
        const val INIT_DISTANCE = 350_800L
        // 上次骑行的距离，单位：米
        const val LAST_DISTANCE = 4800L
    }
}