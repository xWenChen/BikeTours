package com.mustly.biketours

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mustly.biketours.data.DistanceRepository
import com.mustly.biketours.ui.DistanceViewData
import com.mustly.biketours.ui.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val distanceRepo = DistanceRepository()

    val distanceList = MutableLiveData<List<DistanceViewData>>()

    fun init() {
        loadDistanceList()
    }

    private fun loadDistanceList() {
        viewModelScope.launch(Dispatchers.IO) {
            val set = distanceRepo.getDistanceList()
            val list = mutableListOf<DistanceViewData>()
            set.forEach {
                list.add(DistanceViewData(ViewType.NUMBER, it.toLong()))
            }
            // 添加加号
            list.add(DistanceViewData(ViewType.ADD_ICON))

            distanceList.postValue(list)

        }
    }
}