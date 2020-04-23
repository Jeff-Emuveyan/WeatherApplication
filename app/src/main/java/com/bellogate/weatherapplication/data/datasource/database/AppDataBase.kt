package com.bellogate.weatherapplication.data.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bellogate.weatherapplication.data.datasource.database.dao.WeatherForecastDao
import com.bellogate.weatherapplication.data.datasource.database.pojo.WeatherForecast

@Database(entities = [WeatherForecast::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun WeatherForecastDao(): WeatherForecastDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "memo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}