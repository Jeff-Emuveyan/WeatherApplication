package com.bellogate.weatherapplication.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bellogate.weatherapplication.data.datasource.database.pojo.WeatherForecast

@Dao
interface WeatherForecastDao {


    /***
     * Save weather forecast
     * **/
    @Insert
    suspend fun saveWeatherForecast(weatherForecast: WeatherForecast)


    /**
     * Get weather forecast
     ***/
    @Query("SELECT * FROM weatherForecast WHERE cityName LIKE :cityName")
    suspend fun getWeatherForecastSynchronously(cityName: String): WeatherForecast?


    /***
     * Update weather forecast
     * **/
    @Update
    suspend fun updateWeatherForecast(weatherForecast: WeatherForecast)
}