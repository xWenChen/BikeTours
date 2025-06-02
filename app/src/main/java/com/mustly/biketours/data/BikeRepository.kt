package com.mustly.biketours.data

import com.mustly.biketours.database.bean.BikeData

/**
 * Repository
 *   DataSource
 *      Dao
 * */
class BikeRepository {
    private val dataSource = BikeDataSource()

    suspend fun insertOrUpdateBikeData(data: BikeData?): Boolean {
        return dataSource.insertOrUpdateBikeData(data)
    }
}