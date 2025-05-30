package com.mustly.biketours.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustly.biketours.MainApplication
import com.mustly.biketours.database.bean.BikeData
import com.mustly.biketours.database.constants.databaseName
import com.mustly.biketours.database.dao.BikeDataDao

@Database(
    entities = [BikeData::class],
    version = 1
)
abstract class BikeDatabase : RoomDatabase() {
    abstract fun bikeDataDao(): BikeDataDao

    companion object {
        val db = Room.databaseBuilder(
            MainApplication.context,
            BikeDatabase::class.java,
            databaseName
        ).build()
    }
}