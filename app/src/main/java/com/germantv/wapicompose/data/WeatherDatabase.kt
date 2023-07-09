package com.germantv.wapicompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.germantv.wapicompose.model.Favorite
import com.germantv.wapicompose.model.Unit

@Database(entities = [Favorite::class,Unit::class], version = 4, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}