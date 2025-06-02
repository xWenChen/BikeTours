package com.mustly.biketours.data

import android.util.Log
import com.mustly.biketours.database.BikeDatabase
import com.mustly.biketours.database.bean.BikeData

class BikeDataSource {
    private val dao = BikeDatabase.db.bikeDataDao()

    suspend fun insertOrUpdateBikeData(data: BikeData?): Boolean {
        data ?: return false
        try {
            val dbData = dao.query(data)
            val count = if (dbData == null) {
                // 插入
                dao.insertAll(data)
            } else {
                // 更新
                data.id = dbData.id
                dao.updateAll(data)
            }

            return count > 0
        } catch (e: Exception) {
            Log.e(TAG, "", e)
            return false
        }
    }

    companion object {
        const val TAG = "BikeDataSource"
    }
}